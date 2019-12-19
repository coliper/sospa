package org.coliper.sospa;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Predicate;

public class ApiMethodMatcher implements Predicate<Method> {
    private final Predicate<Method> finalCondition;

    public ApiMethodMatcher() {
        this(p -> true);
    }

    public ApiMethodMatcher(Predicate<Method> finalCondition) {
        Objects.requireNonNull(finalCondition, "finalCondition");
        this.finalCondition = finalCondition;
    }

    @Override
    public boolean test(Method method) {
        Objects.requireNonNull(method, "method");
        int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)
                && !isVuelinPageApiMethod(method) && this.finalCondition.test(method);
    }

    private boolean isVuelinPageApiMethod(Method method) {
        try {
            SospaPage.class.getMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        } catch (SecurityException e) {
            throw new RuntimeException("unexpected exception", e);
        }
    }
}
