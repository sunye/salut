package org.atlanmod.verifier;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class EqualsVerifier {

    public static void verify(Class klass, Object[] args1, Object[] args2) {
        Constructor constructor = klass.getConstructors()[0];

        try {
            Object[] differents = new Object[args1.length];
            for (int i = 0; i < args1.length; i++) {
                Object[] arguments = Arrays.copyOf(args1, args1.length);
                arguments[i] = args2[i];
                differents[i] = constructor.newInstance(arguments);
            }

            Object one = constructor.newInstance(args1);
            Object clone = constructor.newInstance(args1);
            Object other = constructor.newInstance(args2);

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

            for (int i = 0; i < differents.length; i++) {
                assertThat(one).isNotEqualTo(differents[i]);
                assertThat(one.hashCode()).isNotEqualTo(differents[i].hashCode());
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
