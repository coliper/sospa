package org.coliper.sospa.sample.page;

import org.coliper.sospa.PageSpecificData;
import org.coliper.sospa.SospaPage;
import org.coliper.sospa.ViewChange;
import org.coliper.sospa.sample.GlobalData;

public class ProductPage extends SospaPage<GlobalData, ProductPage.ClientSideData, Object> {

    public static class ClientSideData {
        // required to avoid serialization error
        public String getDummy() {
            return null;
        }
    }

    public static class ServerSideRenderingData {

    }

    public ProductPage() {
    }

    @Override
    public PageSpecificData<ProductPage.ClientSideData> createPageSpecificData(
            GlobalData globalClientData, Object pageSetupData) {
        Object pageServersideRenderingData = new ServerSideRenderingData();
        ClientSideData pageClientData = new ClientSideData();
        return new PageSpecificData<ProductPage.ClientSideData>(pageClientData,
                pageServersideRenderingData);
    }

    @Override
    public ViewChange<GlobalData, ClientSideData, Object> openFromClient(
            GlobalData globalClientData) {
        return super.openFromClient(globalClientData);

    }

}
