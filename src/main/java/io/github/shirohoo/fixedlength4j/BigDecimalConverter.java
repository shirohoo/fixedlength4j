package io.github.shirohoo.fixedlength4j;

import java.math.BigDecimal;

final class BigDecimalConverter extends AbstractConverter<BigDecimal> {
    @Override
    protected String asString(BigDecimal value, Fixed annotation) {
        return value.toString();
    }

    @Override
    protected BigDecimal asObject(String value, Fixed annotation) {
        return new BigDecimal(value);
    }
}
