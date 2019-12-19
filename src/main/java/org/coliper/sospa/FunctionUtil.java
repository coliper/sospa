package org.coliper.sospa;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

abstract class FunctionUtil {

    static <T> Supplier<T> createDefaultConstructorSupplier(Class<T> globalViewDataType) {
        Constructor<T> ctor;
        try {
            ctor = globalViewDataType.getConstructor();
        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("Class " + globalViewDataType
                    + " does not have a public default constructor which is required if no "
                    + "supplier is given.");
        }

        return () -> {
            try {
                return ctor.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException("Unexpected exception when calling default "
                        + "constructor of class " + globalViewDataType + ".", e);
            }
        };
    }

}
