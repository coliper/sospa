package org.coliper.sospa;

import javax.annotation.Nullable;

public abstract class SospaPage<G, P, S> implements SospaApi {
    private Sospa<G> sospa;

    public PageName<?> getPageName() {
        return new PageName(this.getClass().getSimpleName());
    }

    public TemplateName getTemplateName() {
        return new TemplateName(this.getPageName().getNameAsString());
    }

    protected abstract PageSpecificData<P> createPageSpecificData(@Nullable G globalClientData,
            @Nullable S pageSetupData);

    protected ViewChange<G, P, S> openFromClient(G globalClientData) {
        ViewChange<G, P, S> viewChange = new ViewChange<>();
        S pageSetupData = null;
		PageSpecificData<P> pageData = this.createPageSpecificData(globalClientData, pageSetupData );
        String pageSource = this.sospa.renderingEngine().render(this.getTemplateName(),
                pageData.getPageServersideRenderingData());
        viewChange.setPageHtmlSource(pageSource);
        viewChange.setPageData(pageData.getPageClientData());
        return viewChange;
    }

    void setSospa(Sospa<G> sospa) {
        this.sospa = sospa;
    }

}
