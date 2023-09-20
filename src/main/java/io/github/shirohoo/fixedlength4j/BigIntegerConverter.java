package io.github.shirohoo.fixedlength4j;

import java.math.BigInteger;

final class BigIntegerConverter extends AbstractConverter<BigInteger> {
    @Override
    protected String asString(BigInteger value, Fixed annotation) {
        return value.toString();
    }

    @Override
    protected BigInteger asObject(String value, Fixed annotation) {
        return new BigInteger(value);
    }
}
