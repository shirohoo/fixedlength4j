package io.github.shirohoo.fixedlength4j;

final class StringConverter extends AbstractConverter<String> {
    @Override
    public String asString(String value, Fixed annotation) {
        return value;
    }

    @Override
    public String asObject(String value, Fixed annotation) {
        return value;
    }
}
