package org.coliper.sospa;

public class PageResponse<G, P, S> extends ViewChange<G, P, S> {

    public PageResponse() {}

    @Override
    public void setNameOfNewPage(PageName<? extends SospaPage<G, P, S>> nameOfNewPage) {
        super.setNameOfNewPage(nameOfNewPage);
    }



}
