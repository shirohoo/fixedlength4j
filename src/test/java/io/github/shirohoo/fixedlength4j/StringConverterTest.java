package io.github.shirohoo.fixedlength4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.reflect.annotation.AnnotationParser.annotationForMap;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StringConverterTest {
    StringConverter sut = new StringConverter();

    @Test
    void test_convertToString_left_align() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.LEFT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString("john", annotation);

        // then
        assertEquals("john      ", actual);
    }

    @Test
    void test_convertToString_right_align() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString("john", annotation);

        // then
        assertEquals("      john", actual);
    }

    @Test
    void test_convertToObject_left_align() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.LEFT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToObject("john      ", annotation);

        // then
        assertEquals("john", actual);
    }

    @Test
    void test_convertToObject_right_align() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToObject("      john", annotation);

        // then
        assertEquals("john", actual);
    }
}
