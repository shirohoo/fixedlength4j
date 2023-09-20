package io.github.shirohoo.fixedlength4j;

import java.math.BigDecimal;

final class DoubleConverter extends AbstractConverter<Double> {
    @Override
    public String asString(Double value, Fixed annotation) {
        return String.valueOf(value);
    }

    @Override
    public Double asObject(String value, Fixed annotation) {
        return new BigDecimal(value).doubleValue();
    }
}
