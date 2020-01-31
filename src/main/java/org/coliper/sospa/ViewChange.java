package org.coliper.sospa;

public class ViewChange<G, P, S> {
    private PageName<? extends SospaPage<G, P, S>> pageName;
    private String pageHtmlSource;
    private P pageData;
    private G globalData;
    // private G globalClientData;

    public ViewChange() {
    }

    /**
     * @return the pageHtmlSource
     */
    public String getPageHtmlSource() {
        return pageHtmlSource;
    }

    /**
     * @param pageHtmlSource
     *            the pageHtmlSource to set
     */
    public void setPageHtmlSource(String pageHtmlSource) {
        this.pageHtmlSource = pageHtmlSource;
    }

    /**
     * @return the pageName
     */
    public PageName<? extends SospaPage<G, P, S>> getPageName() {
        return pageName;
    }

    /**
     * @param pageName
     *            the pageName to set
     */
    public void setPageName(PageName<? extends SospaPage<G, P, S>> pageName) {
        this.pageName = pageName;
    }

    /**
     * @return the pageData
     */
    public P getPageData() {
        return pageData;
    }

    /**
     * @param pageData
     *            the pageData to set
     */
    public void setPageData(P pageData) {
        this.pageData = pageData;
    }

    /**
     * @return the globalData
     */
    public G getGlobalData() {
        return globalData;
    }

    /**
     * @param globalData
     *            the globalData to set
     */
    public void setGlobalData(G globalData) {
        this.globalData = globalData;
    }

}
