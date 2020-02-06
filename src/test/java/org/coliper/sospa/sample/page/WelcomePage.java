package org.coliper.sospa.sample.page;

import org.coliper.sospa.PageSpecificData;
import org.coliper.sospa.SospaPage;
import org.coliper.sospa.ViewChange;
import org.coliper.sospa.sample.GlobalData;

public class WelcomePage extends SospaPage<GlobalData, WelcomePage.ClientSideData, Object> {

    public static class ClientSideData {
        private boolean zipCodeValid = false;

        // required to avoid serialization error
        public String getDummy() {
            return null;
        }
    }

    public static class ServerSideRenderingData {

    }

    public WelcomePage() {
    }

    @Override
    public PageSpecificData<WelcomePage.ClientSideData> createPageSpecificData(
            GlobalData globalClientData, Object pageSetupData) {
        Object pageServersideRenderingData = new ServerSideRenderingData();
        ClientSideData pageClientData = new ClientSideData();
        return new PageSpecificData<WelcomePage.ClientSideData>(pageClientData,
                pageServersideRenderingData);
    }

    @Override
    public ViewChange<GlobalData, WelcomePage.ClientSideData, Object> openFromClient(
            GlobalData globalClientData) {
        return super.openFromClient(globalClientData);

    }

}
