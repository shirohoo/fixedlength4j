package io.github.shirohoo.fixedlength4j;

final class BooleanConverter extends AbstractConverter<Boolean> {
    @Override
    public String asString(Boolean value, Fixed annotation) {
        return annotation.isBooleanNumeric() ? convertBooleanNumeric(value) : String.valueOf(value);
    }

    private String convertBooleanNumeric(boolean bool) {
        return bool ? "1" : "0";
    }

    @Override
    public Boolean asObject(String value, Fixed annotation) {
        // If "1" or "0"
        if (annotation.isBooleanNumeric()) {
            if ("1".equals(value)) return Boolean.TRUE;
            else if ("0".equals(value)) return Boolean.FALSE;
            else throw new IllegalArgumentException("Invalid boolean value: " + value);
        }

        // If "true" or "false"
        return Boolean.valueOf(value);
    }
}
