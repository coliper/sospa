package org.coliper.vuelin;

public class PageResponse<G, P, S> extends ViewChange<G, P, S> {

    public PageResponse() {}

    @Override
    public void setNameOfNewPage(PageName<? extends VuelinPage<G, P, S>> nameOfNewPage) {
        super.setNameOfNewPage(nameOfNewPage);
    }



}
