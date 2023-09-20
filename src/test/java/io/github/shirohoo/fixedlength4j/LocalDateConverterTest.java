package io.github.shirohoo.fixedlength4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.reflect.annotation.AnnotationParser.annotationForMap;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class LocalDateConverterTest {
    LocalDateConverter sut = new LocalDateConverter();

    @Test
    void test_convertToString() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 12);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        memberValues.put("pattern", "yyyy-MM-dd");
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString(LocalDate.of(2020, 12, 31), annotation);

        // then
        assertEquals("  2020-12-31", actual);
    }

    @Test
    void test_convertToObject() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 12);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        memberValues.put("pattern", "yyyy-MM-dd");
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        LocalDate actual = sut.convertToObject("  2020-12-31", annotation);

        // then
        assertEquals(LocalDate.of(2020, 12, 31), actual);
    }
}
