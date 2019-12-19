package org.coliper.sospa;

import java.util.Objects;
import io.javalin.http.Context;

class PageLoadEndpoint<G, P, S> extends Endpoint {
    private static final String EMPTY_PATH_EXTENSION = "";

    private final Vuelin<G> vuelinInstance;
    private final VuelinPage<G, P, S> page;

    public PageLoadEndpoint(String rootPath, VuelinPage<G, P, S> page,
            Vuelin<G> vuelinInstance) {
        super(rootPath, page.getPageName(), EMPTY_PATH_EXTENSION);
        Objects.requireNonNull(page, "page");
        Objects.requireNonNull(vuelinInstance, "vuelinInstance");
        this.page = page;
        this.vuelinInstance = vuelinInstance;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        G globalData = this.vuelinInstance.globalViewDataType()
                .cast(ctx.bodyAsClass(this.vuelinInstance.globalViewDataType()));
        S pageSetupData = null;
        PageSpecificData<P> pageData = this.page.createPageSpecificData(globalData, pageSetupData);
        final String clientSideTemplate =
                this.renderServerTemplate(pageData.getPageServersideRenderingData());
        sendResponse(ctx, pageData, clientSideTemplate);
    }

    private void sendResponse(Context ctx, PageSpecificData<P> pageData,
            final String clientSideTemplate) {
        final InternalViewChangeResponse response = new InternalViewChangeResponse();
        response.setNewPageTemplate(clientSideTemplate);
        response.setOverwritingPageData(pageData.getPageClientData());
        ctx.json(response);
    }

    private String renderServerTemplate(Object pageServersideRenderingData) {
        return this.vuelinInstance.renderingEngine().render(page.getTemplateName(),
                pageServersideRenderingData);
    }

}
