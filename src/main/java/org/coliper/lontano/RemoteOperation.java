package org.coliper.lontano;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.google.common.base.Throwables;
import com.google.common.primitives.Primitives;

class RemoteOperation {
    private final RemoteOperationName name;
    private final Object targetObject;
    private final Method method;
    private final Class<?>[] parameterTypes;

    private static Class<?>[] retrieveParameterTypes(Method method) {
        final Class<?>[] types = method.getParameterTypes();
        for (int i = 0; i < types.length; i++) {
            if (types[i].isPrimitive()) {
                types[i] = Primitives.wrap(types[i]);
            }
        }
        return types;
    }

    RemoteOperation(RemoteOperationName name, Object targetObject, Method method) {
        this.name = name;
        this.targetObject = targetObject;
        this.method = method;
        this.parameterTypes = retrieveParameterTypes(method);
    }

    Object call(Object[] parameters) throws Exception {
        try {
            return method.invoke(this.targetObject, requireNonNull(parameters, "parameters"));
        } catch (InvocationTargetException e) {
            Throwables.throwIfUnchecked(e.getTargetException());
            throw (Exception) e.getTargetException();
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException("unexpected exception " + e, e);
        }
    }

    Class<?>[] parameterTypes() {
        return parameterTypes;
    }

    RemoteOperationName name() {
        return this.name;
    }

    OperationMetaInfo getMetaInfo() {
        return new OperationMetaInfo() {

            @Override
            public RemoteOperationName getName() {
                return RemoteOperation.this.name;
            }

            @Override
            public String getCommaSeparatedParams() {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < RemoteOperation.this.method.getParameterCount(); i++) {
                    if (i > 0) {
                        str.append(", ");
                    }
                    str.append("p");
                    str.append(i + 1);
                }
                return str.toString();
            }

            @Override
            public String getOperationName() {
                return RemoteOperation.this.name.valueAsString();
            }
        };
    }

    @Override
    public String toString() {
        return "RemoteOperation [targetObject=" + targetObject + ", method=" + method
                + ", parameterTypes=" + Arrays.toString(parameterTypes) + "]";
    }

}
