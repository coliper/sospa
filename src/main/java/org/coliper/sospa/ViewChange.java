package org.coliper.sospa;

public abstract class ViewChange<G, P, S> {
    private PageName<? extends SospaPage<G, P, S>> nameOfNewPage;
    // private G globalClientData;

    public ViewChange() {}

    public PageName<? extends SospaPage<G, P, S>> getNameOfNewPage() {
        return nameOfNewPage;
    }

    protected void setNameOfNewPage(PageName<? extends SospaPage<G, P, S>> nameOfNewPage) {
        this.nameOfNewPage = nameOfNewPage;
    }

}
