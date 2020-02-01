package org.coliper.lontano;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.google.common.base.Throwables;
import com.google.common.primitives.Primitives;

class RemoteOperation {
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

    RemoteOperation(Object targetObject, Method method) {
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

	@Override
	public String toString() {
		return "RemoteOperation [targetObject=" + targetObject + ", method=" + method + ", parameterTypes="
				+ Arrays.toString(parameterTypes) + "]";
	}
    
}
