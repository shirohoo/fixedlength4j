package io.github.shirohoo.fixedlength4j;

final class LongConverter extends AbstractConverter<Long> {
    @Override
    public String asString(Long value, Fixed annotation) {
        return String.valueOf(value);
    }

    @Override
    public Long asObject(String value, Fixed annotation) {
        return Long.valueOf(value);
    }
}
