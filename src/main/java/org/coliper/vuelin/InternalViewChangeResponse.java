package org.coliper.vuelin;

public class InternalViewChangeResponse {
    private String newPageTemplate;
    private Object overwritingPageData;
    private String mergablePageData;

    public InternalViewChangeResponse() {}

    public String getNewPageTemplate() {
        return newPageTemplate;
    }

    public void setNewPageTemplate(String newPageTemplate) {
        this.newPageTemplate = newPageTemplate;
    }

    public Object getOverwritingPageData() {
        return overwritingPageData;
    }

    public void setOverwritingPageData(Object overwritingPageData) {
        this.overwritingPageData = overwritingPageData;
    }

    public String getMergablePageData() {
        return mergablePageData;
    }

    public void setMergablePageData(String mergablePageData) {
        this.mergablePageData = mergablePageData;
    }

}
