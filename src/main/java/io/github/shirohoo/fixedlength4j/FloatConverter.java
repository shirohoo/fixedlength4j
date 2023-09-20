package io.github.shirohoo.fixedlength4j;

import java.math.BigDecimal;

final class FloatConverter extends AbstractConverter<Float> {
    @Override
    public String asString(Float value, Fixed annotation) {
        return String.valueOf(value);
    }

    @Override
    public Float asObject(String value, Fixed annotation) {
        return new BigDecimal(value).floatValue();
    }
}
