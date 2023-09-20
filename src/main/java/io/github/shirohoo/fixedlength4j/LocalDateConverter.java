package io.github.shirohoo.fixedlength4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

final class LocalDateConverter extends AbstractConverter<LocalDate> {
    @Override
    public String asString(LocalDate value, Fixed annotation) {
        return value.format(DateTimeFormatter.ofPattern(annotation.pattern()));
    }

    @Override
    public LocalDate asObject(String value, Fixed annotation) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(annotation.pattern()));
    }
}
