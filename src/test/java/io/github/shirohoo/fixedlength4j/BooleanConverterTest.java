package io.github.shirohoo.fixedlength4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.reflect.annotation.AnnotationParser.annotationForMap;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BooleanConverterTest {
    BooleanConverter sut = new BooleanConverter();

    @Test
    void test_convertToString() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 4);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.LEFT);
        memberValues.put("isBooleanNumeric", false);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString(true, annotation);

        // then
        assertEquals("true", actual);
    }

    @Test
    void test_convertToString_when_boolean_number() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 4);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.RIGHT);
        memberValues.put("isBooleanNumeric", true);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString(true, annotation);

        // then
        assertEquals("0001", actual);
    }

    @Test
    void test_convertToObject() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 10);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.LEFT);
        memberValues.put("isBooleanNumeric", false);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        Boolean actual = sut.convertToObject("true      ", annotation);

        // then
        assertEquals(true, actual);
    }

    @Test
    void test_convertToObject_when_boolean_number() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 5);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.RIGHT);
        memberValues.put("isBooleanNumeric", true);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        Boolean actual = sut.convertToObject("00001", annotation);

        // then
        assertEquals(true, actual);
    }
}
