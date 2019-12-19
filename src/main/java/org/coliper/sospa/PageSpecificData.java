package org.coliper.sospa;

public class PageSpecificData<P> {

    private final P pageClientData;
    private final Object pageServersideRenderingData;

    public PageSpecificData(P pageClientData, Object pageServersideRenderingData) {
        this.pageClientData = pageClientData;
        this.pageServersideRenderingData = pageServersideRenderingData;
    }

    @Override
    public String toString() {
        return "ServersideRenderingData [p=" + pageClientData + ", s=" + pageServersideRenderingData
                + "]";
    }

    public P getP() {
        return pageClientData;
    }

    public Object getS() {
        return pageServersideRenderingData;
    }

    public P getPageClientData() {
        return pageClientData;
    }

    public Object getPageServersideRenderingData() {
        return pageServersideRenderingData;
    }

}
