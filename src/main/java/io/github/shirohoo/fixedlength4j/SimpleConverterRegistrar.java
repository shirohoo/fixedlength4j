package io.github.shirohoo.fixedlength4j;

import java.util.Map;

final class SimpleConverterRegistrar extends ConverterRegistrar {
    @Override
    protected void addConverters(Map<Class<?>, Converter<?>> converters) {
        // No converter other than the default converter has been registered.
    }
}
