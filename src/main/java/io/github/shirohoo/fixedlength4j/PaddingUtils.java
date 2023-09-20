package io.github.shirohoo.fixedlength4j;

import java.nio.ByteBuffer;

/**
 * Private utility class for handling padding.
 */
final class PaddingUtils {
    private PaddingUtils() {}

    /**
     * Applies padding to the value.
     * @param value The original value to which the padding will be applied
     * @param annotation The annotation that defines the padding.
     * @return The padded value.
     */
    public static String applyPadding(String value, Fixed annotation) {
        if (value == null) {
            throw new IllegalArgumentException("'value' is null.");
        }
        if (annotation == null) {
            throw new IllegalArgumentException("'annotation' is null.");
        }
        if (value.getBytes().length > annotation.bytes()) {
            throw new IllegalArgumentException(
                    "The bytes of 'value' cannot be greater than the bytes of 'annotation'. (value: "
                            + value.getBytes().length + ", annotation: " + annotation.bytes() + ")");
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(annotation.bytes());

        if (annotation.align().isLeft()) {
            byteBuffer.put(value.getBytes());
            if (byteBuffer.remaining() > 0) {
                byteBuffer.put(annotation.pad().repeat(byteBuffer.remaining()).getBytes());
            }
            return new String(byteBuffer.array());
        }

        int remaining = annotation.bytes() - value.getBytes().length;
        if (remaining > 0) {
            byteBuffer.put(annotation.pad().repeat(remaining).getBytes());
        }
        byteBuffer.put(value.getBytes());
        return new String(byteBuffer.array());
    }

    /**
     * Removes padding from the value.
     * @param value This is the value from which I want to remove the padding.
     * @param annotation The annotation that defines the padding.
     * @return The value without padding.
     */
    public static String removePadding(String value, Fixed annotation) {
        if (value == null) {
            throw new IllegalArgumentException("'value' is null.");
        }
        if (annotation == null) {
            throw new IllegalArgumentException("'annotation' is null.");
        }
        if (value.getBytes().length < annotation.bytes()) {
            throw new IllegalArgumentException(
                    "The bytes of 'value' cannot be less than the bytes of 'annotation'. (value: "
                            + value.getBytes().length + ", annotation: " + annotation.bytes() + ")");
        }

        // If left aligned value, padding is on the right.
        if (annotation.align().isLeft()) {
            return removeRightPadding(value, annotation);
        }

        // If right aligned value, padding is on the left.
        return removeLeftPadding(value, annotation);
    }

    private static String removeLeftPadding(String value, Fixed annotation) {
        String firstChar = value.substring(0, 1);
        if (!annotation.pad().character.equals(firstChar)) {
            return value;
        }

        String excludeFirstChar = value.substring(1);
        return removeLeftPadding(excludeFirstChar, annotation);
    }

    private static String removeRightPadding(String value, Fixed annotation) {
        String lastChar = value.substring(value.length() - 1);
        if (!annotation.pad().character.equals(lastChar)) {
            return value;
        }

        String excludeLastChar = value.substring(0, value.length() - 1);
        return removeRightPadding(excludeLastChar, annotation);
    }
}
