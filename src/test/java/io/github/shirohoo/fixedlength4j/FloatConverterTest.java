package io.github.shirohoo.fixedlength4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.reflect.annotation.AnnotationParser.annotationForMap;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FloatConverterTest {
    FloatConverter sut = new FloatConverter();

    @Test
    void test_convertToString() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 5);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString(1.01F, annotation);

        // then
        assertEquals("01.01", actual);
    }

    @Test
    void test_convertToObject() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 5);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        Float actual = sut.convertToObject("01.01", annotation);

        // then
        assertEquals(1.01F, actual);
    }
}
