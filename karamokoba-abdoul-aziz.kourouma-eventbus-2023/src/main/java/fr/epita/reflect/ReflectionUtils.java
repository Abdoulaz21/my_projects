package fr.epita.reflect;

import fr.epita.utils.Logged;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

/**
 * Utility interface for reflection.
 */
public interface ReflectionUtils extends Logged {

    /**
     * Find any class implementing the given interface, and return it wrapped in an optional.
     *
     * @param interfaceClass   The being implemented by the result class.
     * @param <INTERFACE_TYPE> The interface type.
     * @return An optional of the first found class implementing the given interface.
     */
    static <INTERFACE_TYPE>
    Optional<Class<? extends INTERFACE_TYPE>> implementationOf(@NotNull final Class<INTERFACE_TYPE> interfaceClass, @NotNull final String prefix) {
        final Reflections reflections = new Reflections(prefix);
        return reflections.getSubTypesOf(interfaceClass)
                .stream()
                .filter(ReflectionUtils::isPojoClass)
                .filter(ReflectionUtils::hasDefaultConstructor)
                .findFirst();
    }

    /**
     * Test if the given class is a POJO class, meaning it can't be an inner class, an interface or an anonymous class.
     *
     * @param testedClass  The class being tester for POJOism.
     * @param <CLASS_TYPE> The type of the class.
     * @return {@code true} if testedClass is a POJO, {@code false} otherwise.
     */
    static <CLASS_TYPE>
    boolean isPojoClass(@NotNull final Class<CLASS_TYPE> testedClass) {
        return !(testedClass.isMemberClass()
                || testedClass.isLocalClass()
                || testedClass.isInterface()
                || testedClass.isAnonymousClass());
    }


    /**
     * Find the default Ctor on the given class, wrapped in an {@link Optional}.
     *
     * @param testedClass  The class to search.
     * @param <CLASS_TYPE> The type of the class to search.
     * @return An {@link Optional} wrapping the default Ctor of the given class.
     */
    @SuppressWarnings("unchecked")
    static <CLASS_TYPE>
    Optional<Constructor<CLASS_TYPE>> findDefaultConstructor(@NotNull final Class<CLASS_TYPE> testedClass) {
        return Arrays.stream(testedClass.getConstructors())
                .map(ctor -> (Constructor<CLASS_TYPE>) ctor)
                .filter(ctor -> Modifier.isPublic(ctor.getModifiers()) && ctor.getParameterCount() == 0)
                .findFirst();
    }

    /**
     * Test if the given class has a default Ctor.
     *
     * @param testedClass The class to test.
     * @return {@code true} if the class a default Ctor, {@code false} otherwise.
     */
    static boolean hasDefaultConstructor(@NotNull final Class<?> testedClass) {
        return findDefaultConstructor(testedClass).isPresent();
    }

    /**
     * Invoke the given Ctor, wrapping the result in an {@link Optional} in order to shield the user from exceptions
     * (said exceptions will only be logged as warns).
     *
     * @param ctor         The Ctor to invoke.
     * @param <CLASS_TYPE> The type of the class of the expected value.
     * @return The result of the Ctor invocation, wrapped in an {@link Optional}.
     */
    static <CLASS_TYPE>
    Optional<CLASS_TYPE> invokeDefaultConstructor(final Constructor<CLASS_TYPE> ctor) {
        final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
        try {
            return Optional.of(ctor.newInstance());
        } catch (final IllegalAccessException | InstantiationException | InvocationTargetException exception) {
            logger.warn("Error during default constructor invocation:", exception);
            return Optional.empty();
        }
    }

    /**
     * Create an instance of the given interface, finding the first implementation and invoking its default Ctor. No
     * If the first implementation found has no default Ctor, too bad. Make sure you only have your one implementation
     * to test.
     *
     * @param interfaceClass   The interface we want an instance of an implementation of.
     * @param <INTERFACE_TYPE> The actual type of the interface.
     * @return An instance of the given interface, wrapped in an optional.
     */
    static <INTERFACE_TYPE>
    Optional<? extends INTERFACE_TYPE> instanceOf(@NotNull final Class<INTERFACE_TYPE> interfaceClass, @NotNull final String prefix) {
        return implementationOf(interfaceClass, prefix)
                .flatMap(ReflectionUtils::findDefaultConstructor)
                .flatMap(ReflectionUtils::invokeDefaultConstructor);
    }
}
