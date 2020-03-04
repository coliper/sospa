package org.coliper.lontano;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Resources;

public abstract class AbstractLontanoService<T extends AbstractLontanoService<?>> {

    private static final Predicate<Method> DEFAULT_METHOD_FILTER = new Predicate<Method>() {
        @Override
        public boolean test(Method method) {
            return method.getDeclaringClass() != Object.class;
        }
    };

    private static final String JS_TEMPLATE_NAME = "lontano.js.mustache";
    private static final URL JS_TEMPLATE_URL = Resources.getResource(JS_TEMPLATE_NAME);

    private Map<RemoteInterfaceName, RemoteInterface> interfaceMap = new HashMap<>();
    private List<Function<LontanoMetaInfo, String>> javaScriptBlockCreators = new ArrayList<>();

    protected abstract Object deserializeFromJson(String json, Class<?> expectedType);

    protected Object handleRequest(RemoteInterfaceName ifName, RemoteOperationName opName,
            String requestBody) throws Exception {
        final RemoteInterface intf = this.interfaceMap.get(requireNonNull(ifName, "ifName"));
        System.out.println("******** interfaces: " + this.interfaceMap);
        Preconditions.checkState(intf != null, "unknown interface name %s", ifName);
        return intf.callOperation(opName, JsonUtil.splitJsonArray(requestBody));
    }

    protected String createJsSource() {
        final Mustache mustache =
                new DefaultMustacheFactory().compile(this.createJsTemplate(), JS_TEMPLATE_NAME);
        StringWriter writer = new StringWriter(2000);
        mustache.execute(writer, this.getMetaInfo());
        return writer.toString();
    }

    private Reader createJsTemplate() {
        String template;
        try {
            template = Resources.toString(JS_TEMPLATE_URL, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Function<LontanoMetaInfo, String>> x = this.javaScriptBlockCreators;
        for (Function<LontanoMetaInfo, String> function : x) {
            template += function.apply(null);
        }
        return new StringReader(template);
    }

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }

    public T addInterface(RemoteInterfaceName interfaceName, Object object,
            Predicate<Method> methodFilter) {
        requireNonNull(interfaceName, "interfaceName");
        requireNonNull(object, "object");
        requireNonNull(methodFilter, "methodFilter");
        this.interfaceMap.put(interfaceName, new RemoteInterface(interfaceName, object,
                methodFilter, this::deserializeFromJson));
        return self();
    }

    public T addInterface(Object object, Predicate<Method> methodFilter) {
        return this.addInterface(
                this.createInterfaceNameFromObjectClass(requireNonNull(object, "object")), object,
                methodFilter);
    }

    public T addInterface(RemoteInterfaceName interfaceName, Object object) {
        requireNonNull(interfaceName, "interfaceName");
        requireNonNull(object, "object");
        return this.addInterface(interfaceName, object, DEFAULT_METHOD_FILTER);
    }

    public T addInterface(Object object) {
        return this.addInterface(
                this.createInterfaceNameFromObjectClass(requireNonNull(object, "object")), object);
    }

    public T addJsBlockCreator(Function<LontanoMetaInfo, String> creator) {
        this.javaScriptBlockCreators.add(creator);
        return this.self();
    }

    private RemoteInterfaceName createInterfaceNameFromObjectClass(Object object) {
        return new RemoteInterfaceName(object.getClass().getSimpleName());
    }

    public LontanoMetaInfo getMetaInfo() {
        return new LontanoMetaInfo() {
            @Override
            public List<InterfaceMetaInfo> getInterfaces() {
                return AbstractLontanoService.this.interfaceMap.values().stream()
                        .map(RemoteInterface::getMetaInfo).collect(Collectors.toList());
            }
        };
    }

    protected AbstractLontanoService() {
    }

}
