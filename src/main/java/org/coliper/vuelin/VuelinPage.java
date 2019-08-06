package org.coliper.vuelin;

import javax.annotation.Nullable;

public interface VuelinPage<G, P, S> extends VuelinApi {

    default PageName<?> getPageName() {
        return new PageName(this.getClass().getSimpleName());
    }

    default TemplateName getTemplateName() {
        return new TemplateName(this.getPageName().getNameAsString());
    }

    PageSpecificData<P> createPageSpecificData(@Nullable G globalClientData,
            @Nullable S pageSetupData);
}
