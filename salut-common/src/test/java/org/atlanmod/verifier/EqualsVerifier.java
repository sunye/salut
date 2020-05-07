package org.atlanmod.verifier;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings({"pmd:ClassNamingConventions"})
public class EqualsVerifier {

    public static void verify(Class klass, Object[] args1, Object[] args2) {
        int length = args1.length;
        if (args2.length != length) {
            throw new IllegalArgumentException("Argument arrays must have the same length");
        }
        Class[] arg1Types = new Class[length];
        Class[] arg2Types = new Class[length];
        for (int i = 0; i < length; i++) {
            arg1Types[i] = args1[i].getClass();
            arg2Types[i] = args2[i].getClass();
        }
        Function<Object[], Object> instantiator = getInstantiator(klass, arg1Types);

        /*
        Optional<Constructor> constructor = getConstructor(klass, arg1Types);
        Optional<Constructor> constructorForArgs2 = getConstructor(klass, arg2Types);
        if (!constructor.equals(constructorForArgs2)) {
            throw new IllegalArgumentException(
                "Argument arrays must have compatible types (use the same constructor");
        }
         */

        Object[] different = new Object[args1.length];
        for (int i = 0; i < args1.length; i++) {
            Object[] arguments = Arrays.copyOf(args1, args1.length);
            arguments[i] = args2[i];
            different[i] = instantiator.apply(arguments);
        }

        Object one = instantiator.apply(args1);
        Object clone = instantiator.apply(args1);
        Object other = instantiator.apply(args2);

        assertThat(one.equals(one)).isTrue();

        assertThat(one)
            .isEqualTo(one)
            .isEqualTo(clone)
            .isNotEqualTo(other)
            .isNotEqualTo(null)
            .isEqualTo(one);

        assertThat(one.hashCode())
            .isEqualTo(one.hashCode())
            .isEqualTo(clone.hashCode())
            .isNotEqualTo(other.hashCode())
            .isNotEqualTo(one);


        for (Object each : different) {
            assertThat(one).isNotEqualTo(each);
            assertThat(one.hashCode()).isNotEqualTo(each.hashCode());
        }


    }


    private static Optional<Constructor> getConstructor(Class type, Class[] argumentTypes) {
        return Stream.of(type.getConstructors()).filter(each -> matches(each, argumentTypes))
            .findFirst();
    }

    private static Optional<Method> getFactory(Class type, Class[] argumentTypes) {
        return Stream.of(type.getMethods())
            .filter(each -> Modifier.isStatic(each.getModifiers()))
            .filter(each -> each.getReturnType().isAssignableFrom(type))
            .filter(each -> matches(each, argumentTypes))
            .findFirst();
    }

    private static Function<Object[], Object> getInstantiator(Class type, Class[] argumentTypes) {
        Optional<Constructor> constructor = getConstructor(type, argumentTypes);
        if (constructor.isPresent()) {
            return arguments -> {
                try {
                    return constructor.get().newInstance(arguments);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Could not instantiate class with constructor");
                }
            };
        } else {
            Optional<Method> factory = getFactory(type, argumentTypes);
            if (factory.isPresent()) {
                return arguments -> {
                    try {
                        return factory.get().invoke(null, arguments);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException(
                            "Could not instantiate class with factory method");
                    }
                };
            } else {
                throw new IllegalArgumentException(
                    "Could not find compatible constructor or factory method");
            }
        }
    }

    private static boolean matches(Executable executable, Class[] argumentTypes) {
        Class[] types = executable.getParameterTypes();
        if (types.length != argumentTypes.length) {
            return false;
        }
        for (int i = 0; i < argumentTypes.length; i++) {
            if (!types[i].isAssignableFrom(argumentTypes[i])) {
                return false;
            }
        }
        return true;
    }
}
