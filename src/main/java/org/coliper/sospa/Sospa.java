package org.coliper.sospa;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.coliper.lontano.AbstractLontanoService;

import com.google.common.base.Preconditions;

public class Sospa<T> {
    private static final String DEFALUT_ROOT_PATH = "";

    private final String rootPath = DEFALUT_ROOT_PATH;
    private final Class<T> globalViewDataType;
    private final Supplier<T> globalViewObjectSupplier;
    private final AbstractLontanoService<?> lontano;
    // move both to builder as not needed later?
    private List<SospaPage<T, ?, ?>> pageList = new ArrayList<>();
    private List<SospaCommonApi> apiList = new ArrayList<>();
    private ApiMethodMatcher apiMethodMatcher = new ApiMethodMatcher();
    private ServersideRenderingEngine renderingEngine = new ThymeleafRenderingEngine();

    public static boolean isRemoteMethod(Method method) {
        return method.getReturnType() == ViewChange.class;
    }

    public Sospa(AbstractLontanoService<?> lontano, Class<T> globalViewDataType,
            Supplier<T> globalViewObjectSupplier) {
        this.globalViewDataType = requireNonNull(globalViewDataType, "globalViewDataType");
        this.globalViewObjectSupplier =
                requireNonNull(globalViewObjectSupplier, "globalViewObjectSupplier");
        this.lontano = requireNonNull(lontano, "lontano");
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
        this.lontano.addInterface(page, Sospa::isRemoteMethod);
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
