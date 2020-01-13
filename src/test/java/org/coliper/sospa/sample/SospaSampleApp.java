package org.coliper.sospa.sample;

import java.util.ArrayList;
import java.util.List;

import org.coliper.sospa.Sospa;
import org.coliper.sospa.SospaPage;
import org.coliper.sospa.sample.page.ProductPage;
import org.coliper.sospa.sample.page.WelcomePage;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class SospaSampleApp {

	public static void main(String[] args) {
		System.out.println("Vuelin Sample Application");
		Javalin javalin = Javalin.create(config -> {
			config.addStaticFiles("src/test/resources/static-web", Location.EXTERNAL).enableDevLogging();

		});
		new Sospa<GlobalData>(GlobalData.class).addPages(createPageList()).registerWithJavalin(javalin);
		javalin.start();
	}

	private static List<SospaPage<GlobalData, ?, ?>> createPageList() {
		List<SospaPage<GlobalData, ?, ?>> pageList = new ArrayList<>();
		pageList.add(new WelcomePage());
		pageList.add(new ProductPage());
		return pageList;
	}

}
