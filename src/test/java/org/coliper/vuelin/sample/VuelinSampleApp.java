package org.coliper.vuelin.sample;

import java.util.ArrayList;
import java.util.List;

import org.coliper.vuelin.Vuelin;
import org.coliper.vuelin.VuelinPage;
import org.coliper.vuelin.sample.page.ProductPage;
import org.coliper.vuelin.sample.page.WelcomePage;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class VuelinSampleApp {

	public static void main(String[] args) {
		System.out.println("Vuelin Sample Application");
		Javalin javalin = Javalin.create(config -> {
			config.addStaticFiles("src/test/resources/static-web", Location.EXTERNAL).enableDevLogging();

		});
		new Vuelin.Builder<GlobalData>(GlobalData.class).javalin(javalin).addPages(createPageList()).build();
		javalin.start();
	}

	private static List<VuelinPage<GlobalData, ?, ?>> createPageList() {
		List<VuelinPage<GlobalData, ?, ?>> pageList = new ArrayList<>();
		pageList.add(new WelcomePage());
		pageList.add(new ProductPage());
		return pageList;
	}

}
