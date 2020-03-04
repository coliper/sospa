package org.coliper.lontano;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class RemoteInterface {
    private final RemoteInterfaceName name;
    private final Map<RemoteOperationName, RemoteOperation> operationMap;
    private final BiFunction<String, Class<?>, Object> jsonDeserializer;

    private static Map<RemoteOperationName, RemoteOperation> createOperationMap(Object targetObject,
            Predicate<Method> methodFilter) {
        final Map<RemoteOperationName, RemoteOperation> map = new HashMap<>();
        Method[] methods = targetObject.getClass().getMethods();
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue; // skip static methods
            }
            if (!methodFilter.test(method)) {
                continue; // skip methods of class Object
            }
            final RemoteOperationName opName = new RemoteOperationName(method.getName());
            map.put(opName, new RemoteOperation(opName, targetObject, method));
        }
        return Collections.unmodifiableMap(map);
    }

    RemoteInterface(RemoteInterfaceName name, Object targetObject, Predicate<Method> methodFilter,
            BiFunction<String, Class<?>, Object> jsonDeserializer) {
        this.name = requireNonNull(name, "name");
        this.operationMap = createOperationMap(requireNonNull(targetObject, "targetObject"),
                requireNonNull(methodFilter, "methodFilter"));
        this.jsonDeserializer = requireNonNull(jsonDeserializer, "jsonDeserializer");
    }

    RemoteInterfaceName name() {
        return this.name;
    }

    Set<RemoteOperationName> operationNames() {
        return this.operationMap.keySet();
    }

    Object callOperation(RemoteOperationName opName, String[] jsonParameters) throws Exception {
        requireNonNull(jsonParameters, "jsonParameters");
        final RemoteOperation operation = this.operationMap.get(requireNonNull(opName, "opName"));
        checkState(operation != null, "unknown operation %s", opName);
        Object[] parameters = parseJsonParameters(jsonParameters, operation.parameterTypes());
        return operation.call(parameters);
    }

    InterfaceMetaInfo getMetaInfo() {
        return new InterfaceMetaInfo() {

            @Override
            public RemoteInterfaceName getName() {
                return RemoteInterface.this.name;
            }

            @Override
            public List<OperationMetaInfo> getOperations() {
                return RemoteInterface.this.operationMap.values().stream()
                        .map(RemoteOperation::getMetaInfo).collect(Collectors.toList());
            }

            @Override
            public String getInterfaceName() {
                return RemoteInterface.this.name.valueAsString();
            }
        };
    }

    private Object[] parseJsonParameters(String[] jsonParameters,
            Class<?>[] expectedParameterTypes) {
        Object[] parameters = new Object[jsonParameters.length];
        checkState(jsonParameters.length == expectedParameterTypes.length,
                "unexpected no of parameters");
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] =
                    this.jsonDeserializer.apply(jsonParameters[i], expectedParameterTypes[i]);
        }
        return parameters;
    }

    @Override
    public String toString() {
        return "RemoteInterface [name=" + name + ", operationMap=" + operationMap
                + ", jsonDeserializer=" + jsonDeserializer + "]";
    }
}
