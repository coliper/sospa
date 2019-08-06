package org.coliper.vuelin.sample;

import org.coliper.vuelin.Vuelin;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class VuelinSampleApp {

    public static void main(String[] args) {
        System.out.println("Vuelin Sample Application");
        Javalin javalin = Javalin.create(config -> {
            config.addStaticFiles("src/test/resources/static-web", Location.EXTERNAL)
                    .enableDevLogging();

        });
        new Vuelin.Builder<Object>(Object.class).javalin(javalin).build();
        javalin.start();
    }

}
