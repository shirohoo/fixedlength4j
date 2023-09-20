package io.github.shirohoo.fixedlength4j;

final class IntegerConverter extends AbstractConverter<Integer> {
    @Override
    public String asString(Integer value, Fixed annotation) {
        return String.valueOf(value);
    }

    @Override
    public Integer asObject(String value, Fixed annotation) {
        return Integer.parseInt(value);
    }
}
