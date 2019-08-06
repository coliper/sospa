package org.coliper.vuelin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import com.google.common.base.Preconditions;
import io.javalin.Javalin;

public class Vuelin<T> {
    private final Class<T> globalViewDataType;
    private final Supplier<T> globalViewObjectSupplier;
    private Javalin javalin;
    // move both to builder as not needed later?
    private List<VuelinPage<T, ?, ?>> pageList = new ArrayList<>();
    private List<VuelinCommonApi> apiList = new ArrayList<>();
    private ApiMethodMatcher apiMethodMatcher = new ApiMethodMatcher();
    private ServersideRenderingEngine renderingEngine = new ThymeleafRenderingEngine();

    public static class Builder<T> {
        private static final String DEFALUT_ROOT_PATH = "";

        private final Vuelin<T> vuelin;
        private final String rootPath = DEFALUT_ROOT_PATH;

        public Builder(Class<T> globalViewDataType) {
            this(globalViewDataType,
                    FunctionUtil.createDefaultConstructorSupplier(globalViewDataType));
        }

        public Builder(Class<T> globalViewDataType, Supplier<T> globalViewObjectSupplier) {
            Objects.requireNonNull(globalViewDataType, "globalViewDataType");
            Objects.requireNonNull(globalViewObjectSupplier, "globalViewObjectSupplier");
            this.vuelin = new Vuelin<T>(globalViewDataType, globalViewObjectSupplier);
        }

        public Builder<T> javalin(Javalin javalin) {
            Objects.requireNonNull(javalin, "javalin");
            this.vuelin.javalin = javalin;
            return this;
        }

        public Builder<T> addPage(VuelinPage<T, ?, ?> page) {
            Objects.requireNonNull(page, "page");
            Preconditions.checkArgument(
                    this.vuelin.pageList.stream()
                            .filter(p -> p.getPageName().equals(page.getPageName())).count() == 0,
                    "Page with name '" + page.getPageName().getNameAsString()
                            + "' was already added.");
            this.vuelin.pageList.add(page);
            return this;
        }

        public Vuelin<T> build() {
            if (this.vuelin.javalin == null) {
                this.vuelin.javalin = Javalin.create();
            }
            this.registerEndpoints();
            return this.vuelin;
        }

        private void registerEndpoints() {
            for (VuelinPage<T, ?, ?> page : this.vuelin.pageList) {
                this.registerPageEndpoints(page);
            }
        }

        private void registerPageEndpoints(VuelinPage<T, ?, ?> page) {
            this.registerPageLoadEndpoint(page);
            this.registerPageMethodEndpoints(page);
        }

        private void registerPageMethodEndpoints(VuelinPage<T, ?, ?> page) {
            // TODO Auto-generated method stub

        }

        private void registerPageLoadEndpoint(VuelinPage<T, ?, ?> page) {
            PageLoadEndpoint<T, ?, ?> endpoint =
                    new PageLoadEndpoint<>(this.rootPath, page, this.vuelin);
            endpoint.registerWithJavalin(this.vuelin.javalin);
        }
    }

    private Vuelin(Class<T> globalViewDataType, Supplier<T> globalViewObjectSupplier) {
        this.globalViewDataType = globalViewDataType;
        this.globalViewObjectSupplier = globalViewObjectSupplier;
    }

    public Javalin javalin() {
        return this.javalin;
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
