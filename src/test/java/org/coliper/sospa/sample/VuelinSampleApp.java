package org.coliper.sospa.sample;

import java.util.ArrayList;
import java.util.List;

import org.coliper.sospa.Vuelin;
import org.coliper.sospa.VuelinPage;
import org.coliper.sospa.sample.page.ProductPage;
import org.coliper.sospa.sample.page.WelcomePage;

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
