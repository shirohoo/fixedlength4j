package io.github.shirohoo.fixedlength4j;

/**
 * Abstract class for implementing {@link Converter}.
 * @param <T> Type of the value to be converted
 */
public abstract class AbstractConverter<T> implements Converter<T> {
    @Override
    public String convertToString(T value, Fixed annotation) {
        if (value == null) {
            throw new IllegalArgumentException("'value' must not be null.");
        }
        if (annotation == null) {
            throw new IllegalArgumentException("'annotation' must not be null.");
        }

        String stringValue = asString(value, annotation);
        return PaddingUtils.applyPadding(stringValue, annotation);
    }

    @Override
    public T convertToObject(String value, Fixed annotation) {
        if (value == null) {
            throw new IllegalArgumentException("'value' must not be null.");
        }
        if (annotation == null) {
            throw new IllegalArgumentException("'annotation' must not be null.");
        }

        String stringValue = PaddingUtils.removePadding(value, annotation);
        return asObject(stringValue, annotation);
    }

    /**
     * Convert T to String. You only need to focus on changing types safely.
     * @param value T
     * @param annotation {@link Fixed}
     * @return T converted to String
     */
    protected abstract String asString(T value, Fixed annotation);

    /**
     * Convert String to T. You only need to focus on changing types safely.
     * @param value String
     * @param annotation {@link Fixed}
     * @return String converted to T
     */
    protected abstract T asObject(String value, Fixed annotation);
}
