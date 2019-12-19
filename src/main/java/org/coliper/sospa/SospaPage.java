package org.coliper.sospa;

import javax.annotation.Nullable;

public interface SospaPage<G, P, S> extends SospaApi {

    default PageName<?> getPageName() {
        return new PageName(this.getClass().getSimpleName());
    }

    default TemplateName getTemplateName() {
        return new TemplateName(this.getPageName().getNameAsString());
    }

    PageSpecificData<P> createPageSpecificData(@Nullable G globalClientData,
            @Nullable S pageSetupData);
}
