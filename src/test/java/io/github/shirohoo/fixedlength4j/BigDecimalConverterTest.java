package io.github.shirohoo.fixedlength4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.reflect.annotation.AnnotationParser.annotationForMap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BigDecimalConverterTest {
    BigDecimalConverter sut = new BigDecimalConverter();

    @Test
    void test_convertToString() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 5);
        memberValues.put("pad", Pad.ZERO);
        memberValues.put("align", Align.RIGHT);
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString(BigDecimal.ONE, annotation);

        // then
        assertEquals("00001", actual);
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
        BigDecimal actual = sut.convertToObject("00001", annotation);

        // then
        assertEquals(BigDecimal.ONE, actual);
    }
}
