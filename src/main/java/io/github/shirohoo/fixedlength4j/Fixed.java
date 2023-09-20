package io.github.shirohoo.fixedlength4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify the fixed length of the field.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Fixed {
    /**
     * The length of the field.
     * @return the length of the field
     */
    int bytes();

    /**
     * If you need a specific pattern to parse the field, please specify it.
     * @return the pattern of the field
     */
    String pattern() default "yyyyMMdd";

    /**
     * If the length of the field is less than bytes(), specifies the characters to be padded until it becomes equal to bytes().
     * @return the character to be padded
     */
    Pad pad() default Pad.SPACE;

    /**
     * In one fixed-length data, it means whether the actual data is located on the left or right.
     * If Align.LEFT, the actual data exists on the left, and the remaining space on the right is replaced with padding characters.
     * @return the alignment of the field
     */
    Align align() default Align.LEFT;

    /**
     * If the field is a boolean type, it means whether to convert it to a numeric value.
     * If this value is true, "true" corresponds to "1", and if it is false, "true" is just "true".
     * @return whether to convert boolean to numeric value
     */
    boolean isBooleanNumeric() default true;
}
