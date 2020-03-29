package org.coliper.sospa;

import static java.util.Objects.requireNonNull;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.coliper.lontano.AbstractLontanoService;
import org.coliper.lontano.InterfaceMetaInfo;
import org.coliper.lontano.LontanoMetaInfo;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Sospa<T> {
    private static final String DEFALUT_ROOT_PATH = "";
    private static final String JS_TEMPLATE_NAME = "sospa.js.mustache";

    private final Mustache mustache = new DefaultMustacheFactory().compile(JS_TEMPLATE_NAME);
    private final String rootPath = DEFALUT_ROOT_PATH;
    private final Class<T> globalViewDataType;
    private final Supplier<T> globalViewObjectSupplier;
    private final AbstractLontanoService<?> lontano;
    // move both to builder as not needed later?
    private List<SospaPage<T, ?, ?>> pageList = new ArrayList<>();
    private List<SospaCommonApi> apiList = new ArrayList<>();
    private ApiMethodMatcher apiMethodMatcher = new ApiMethodMatcher();
    private ServersideRenderingEngine renderingEngine = new ThymeleafRenderingEngine();

    private static boolean isRemoteMethod(Method method) {
        return method.getReturnType() == ViewChange.class;
    }

    public Sospa(AbstractLontanoService<?> lontano, Class<T> globalViewDataType,
            Supplier<T> globalViewObjectSupplier) {
        this.globalViewDataType = requireNonNull(globalViewDataType, "globalViewDataType");
        this.globalViewObjectSupplier =
                requireNonNull(globalViewObjectSupplier, "globalViewObjectSupplier");
        this.lontano = requireNonNull(lontano, "lontano");
        this.lontano.addJsBlockCreator(this::createJsBlock);
    }

    private String createJsBlock(LontanoMetaInfo lontanoMeta) {
        StringWriter writer = new StringWriter(2000);
        this.mustache.execute(writer, this.createJsCodeMetaInfo(lontanoMeta));
        return writer.toString();
    }

    private JsCodeMetaInfo createJsCodeMetaInfo(final LontanoMetaInfo lontanoMeta) {
        return new JsCodeMetaInfo() {

            @Override
            public List<PageMetaInfo> getPages() {
                return Lists.transform(Sospa.this.pageList, this::createPageMetaInfo);
            }

            private PageMetaInfo createPageMetaInfo(SospaPage<?, ?, ?> pg) {
                InterfaceMetaInfo interfaceMeta = lontanoMeta
                        .getInterfaceForName(pg.getLontanoInterfaceName())
                        .orElseThrow(() -> new IllegalStateException(
                                "cannot find Lontano interface " + pg.getLontanoInterfaceName()));
                return new PageMetaInfo(pg, interfaceMeta);
            }

            @Override
            public List<ApiMetaInfo> getApis() {
                // TODO Auto-generated method stub
                return null;
            }

        };
    }

    public Sospa(AbstractLontanoService<?> lontano, Class<T> globalViewDataType) {
        this(lontano, globalViewDataType,
                FunctionUtil.createDefaultConstructorSupplier(globalViewDataType));
    }

    public Sospa<T> addPage(SospaPage<T, ?, ?> page) {
        Objects.requireNonNull(page, "page");
        Preconditions.checkArgument(
                this.pageList.stream().filter(p -> p.getPageName().equals(page.getPageName()))
                        .count() == 0,
                "Page with name '" + page.getPageName().getNameAsString() + "' was already added.");
        this.pageList.add(page);
        this.lontano.addInterface(page.getLontanoInterfaceName(), page, Sospa::isRemoteMethod);
        page.setSospa(this);
        return this;
    }

    public Sospa<T> addPages(Collection<SospaPage<T, ?, ?>> pages) {
        for (SospaPage<T, ?, ?> vuelinPage : pages) {
            this.addPage(vuelinPage);
        }
        return this;
    }

    ServersideRenderingEngine renderingEngine() {
        return this.renderingEngine;
    }

    Class<T> globalViewDataType() {
        return globalViewDataType;
    }

    Supplier<T> globalViewObjectSupplier() {
        return globalViewObjectSupplier;
    }
}
