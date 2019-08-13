package org.coliper.vuelin.sample.page;

import org.coliper.vuelin.PageSpecificData;
import org.coliper.vuelin.VuelinPage;
import org.coliper.vuelin.sample.GlobalData;

public class WelcomePage implements VuelinPage<GlobalData, WelcomePage.ClientSideData, Object> {

	public static class ClientSideData {
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
	public PageSpecificData<WelcomePage.ClientSideData> createPageSpecificData(GlobalData globalClientData,
			Object pageSetupData) {
		Object pageServersideRenderingData = new ServerSideRenderingData();
		ClientSideData pageClientData = new ClientSideData();
		return new PageSpecificData<WelcomePage.ClientSideData>(pageClientData, pageServersideRenderingData);
	}

}
