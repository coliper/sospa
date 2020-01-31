package org.coliper.sospa.sample.page;

import org.coliper.sospa.PageSpecificData;
import org.coliper.sospa.SospaPage;
import org.coliper.sospa.sample.GlobalData;

public class ProductPage extends SospaPage<GlobalData, ProductPage.ClientSideData, Object> {

    public static class ClientSideData {

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

}
