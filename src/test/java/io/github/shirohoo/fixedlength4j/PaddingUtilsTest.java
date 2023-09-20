package io.github.shirohoo.fixedlength4j;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.reflect.annotation.AnnotationParser.annotationForMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PaddingUtilsTest {
    @Test
    @SuppressWarnings("DataFlowIssue")
    void test_applyPadding_should_notnull_value() {
        assertThatThrownBy(() -> PaddingUtils.applyPadding(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'value' is null.");
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void test_applyPadding_should_notnull_annotation() {
        assertThatThrownBy(() -> PaddingUtils.applyPadding("", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'annotation' is null.");
    }

    @Test
    void test_applyPadding_left_space() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.LEFT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.applyPadding("john", annotation);

        // then
        assertEquals("john      ", actual);
    }

    @Test
    void test_applyPadding_left_zero() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.LEFT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.applyPadding("john", annotation);

        // then
        assertEquals("john000000", actual);
    }

    @Test
    void test_applyPadding_right_space() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.applyPadding("john", annotation);

        // then
        assertEquals("      john", actual);
    }

    @Test
    void test_applyPadding_right_zero() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.applyPadding("john", annotation);

        // then
        assertEquals("000000john", actual);
    }

    @Test
    void test_applyPadding_should_not_value_greater_then_annotation() {
        // given
        String value = "          ";
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, Collections.singletonMap("bytes", 5));

        // when & then
        assertThatThrownBy(() -> PaddingUtils.applyPadding(value, annotation))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The bytes of 'value' cannot be greater than the bytes of 'annotation'. (value: 10, annotation: 5)");
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void test_removePadding_should_notnull_value() {
        assertThatThrownBy(() -> PaddingUtils.removePadding(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'value' is null.");
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void test_removePadding_should_notnull_annotation() {
        assertThatThrownBy(() -> PaddingUtils.removePadding("", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'annotation' is null.");
    }

    @Test
    void test_removePadding_left_space() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.LEFT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.removePadding("john      ", annotation);

        // then
        assertEquals("john", actual);
    }

    @Test
    void test_removePadding_left_zero() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.LEFT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.removePadding("john000000", annotation);

        // then
        assertEquals("john", actual);
    }

    @Test
    void test_removePadding_right_space() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.removePadding("      john", annotation);

        // then
        assertEquals("john", actual);
    }

    @Test
    void test_removePadding_right_zero() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = PaddingUtils.removePadding("000000john", annotation);

        // then
        assertEquals("john", actual);
    }

    @Test
    void test_removePadding_should_not_value_less_then_annotation() {
        // given
        String value = "     ";
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, Collections.singletonMap("bytes", 10));

        // when & then
        assertThatThrownBy(() -> PaddingUtils.removePadding(value, annotation))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The bytes of 'value' cannot be less than the bytes of 'annotation'. (value: 5, annotation: 10)");
    }
}
