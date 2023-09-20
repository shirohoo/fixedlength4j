package io.github.shirohoo.fixedlength4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.reflect.annotation.AnnotationParser.annotationForMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class LocalDateTimeConverterTest {
    LocalDateTimeConverter sut = new LocalDateTimeConverter();

    @Test
    void test_convertToString() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 25);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        memberValues.put("pattern", "yyyy-MM-dd HH:mm:ss");
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        String actual = sut.convertToString(LocalDateTime.of(2020, 12, 31, 12, 0, 0), annotation);

        // then
        assertEquals("      2020-12-31 12:00:00", actual);
    }

    @Test
    void test_convertToObject() {
        // given
        Map<String, Object> memberValues = new HashMap<>();
        memberValues.put("bytes", 25);
        memberValues.put("pad", Pad.SPACE);
        memberValues.put("align", Align.RIGHT);
        memberValues.put("pattern", "yyyy-MM-dd HH:mm:ss");
        Fixed annotation = (Fixed) annotationForMap(Fixed.class, memberValues);

        // when
        LocalDateTime actual = sut.convertToObject("      2020-12-31 12:00:00", annotation);

        // then
        assertEquals(LocalDateTime.of(2020, 12, 31, 12, 0, 0), actual);
    }
}
