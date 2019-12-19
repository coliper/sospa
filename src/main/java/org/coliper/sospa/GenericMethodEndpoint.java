package org.coliper.sospa;

import java.lang.reflect.Method;
import io.javalin.http.Context;

class GenericMethodEndpoint extends Endpoint {
    private final VuelinApi targetObject;
    private final Method targetMethod;

    private static String createMethodPathExtension(Method method) {
        return URI_PATH_SEPARATOR + method.getName();
    }

    GenericMethodEndpoint(String rootPath, EndpointNamespace namespace,
            VuelinApi targetObject, Method targetMethod) {
        super(rootPath, namespace, createMethodPathExtension(targetMethod));
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        // TODO Auto-generated method stub

    }

}
