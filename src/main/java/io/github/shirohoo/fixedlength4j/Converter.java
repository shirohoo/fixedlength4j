package io.github.shirohoo.fixedlength4j;

/**
 * <p>
 * Converter interface. Implement this interface to convert between String and Object.
 * In most cases, it is more convenient to extend {@link AbstractConverter} than to implement this interface directly.
 * @param <T> the type of the object
 */
public interface Converter<T> {
    String convertToString(T value, Fixed annotation);

    T convertToObject(String value, Fixed annotation);
}
