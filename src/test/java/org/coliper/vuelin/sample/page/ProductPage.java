package org.coliper.vuelin.sample.page;

import org.coliper.vuelin.PageSpecificData;
import org.coliper.vuelin.VuelinPage;
import org.coliper.vuelin.sample.GlobalData;

public class ProductPage implements VuelinPage<GlobalData, ProductPage.ClientSideData, Object> {

    public static class ClientSideData {

    }

    public static class ServerSideRenderingData {

    }

    public ProductPage() {}

    @Override
    public PageSpecificData<ProductPage.ClientSideData> createPageSpecificData(
            GlobalData globalClientData, Object pageSetupData) {
        Object pageServersideRenderingData = new ServerSideRenderingData();
        ClientSideData pageClientData = new ClientSideData();
        return new PageSpecificData<ProductPage.ClientSideData>(pageClientData,
                pageServersideRenderingData);
    }

}
