package org.coliper.sospa;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

abstract class Endpoint implements Handler {
    protected static final String URI_PATH_SEPARATOR = "//";

    private final String path;

    Endpoint(String rootPath, EndpointNamespace namespace, String pathExtension) {
        this.path = rootPath + URI_PATH_SEPARATOR + namespace.getNameAsString() + pathExtension;
    }

    void registerWithJavalin(Javalin javalin) {
        javalin.post(this.path, this);
    }

    @Override
    public abstract void handle(Context ctx) throws Exception;

}
