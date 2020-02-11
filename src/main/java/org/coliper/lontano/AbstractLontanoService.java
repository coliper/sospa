package org.coliper.lontano;

import static java.util.Objects.requireNonNull;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.base.Preconditions;

public abstract class AbstractLontanoService<T extends AbstractLontanoService<?>> {
    private static final Predicate<Method> DEFAULT_METHOD_FILTER = new Predicate<Method>() {
        @Override
        public boolean test(Method method) {
            return method.getDeclaringClass() != Object.class;
        }
    };

    private static final String JS_TEMPLATE_NAME = "lontano.js.mustache";

    private final Mustache mustache = new DefaultMustacheFactory().compile(JS_TEMPLATE_NAME);
    private Map<RemoteInterfaceName, RemoteInterface> interfaceMap = new HashMap<>();

    protected abstract Object deserializeFromJson(String json, Class<?> expectedType);

    protected Object handleRequest(RemoteInterfaceName ifName, RemoteOperationName opName,
            String requestBody) throws Exception {
        final RemoteInterface intf = this.interfaceMap.get(requireNonNull(ifName, "ifName"));
        System.out.println("******** interfaces: " + this.interfaceMap);
        Preconditions.checkState(intf != null, "unknown interface name %s", ifName);
        return intf.callOperation(opName, JsonUtil.splitJsonArray(requestBody));
    }

    protected String createJsSource() {
        StringWriter writer = new StringWriter(2000);
        Map<String, Object> meta = new HashMap<String, Object>();
        this.mustache.execute(writer, meta);
        return writer.toString();
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

    private RemoteInterfaceName createInterfaceNameFromObjectClass(Object object) {
        return new RemoteInterfaceName(object.getClass().getSimpleName());
    }

    protected AbstractLontanoService() {
    }

}
