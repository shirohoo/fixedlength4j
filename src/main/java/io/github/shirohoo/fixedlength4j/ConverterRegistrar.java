package io.github.shirohoo.fixedlength4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class ConverterRegistrar {
    private final Map<Class<?>, Converter<?>> converters;

    public ConverterRegistrar() {
        Map<Class<?>, Converter<?>> converters = defaultConverters();
        addConverters(converters);
        this.converters = converters;
    }

    private Map<Class<?>, Converter<?>> defaultConverters() {
        Map<Class<?>, Converter<?>> converters = new HashMap<>();

        converters.put(String.class, new StringConverter());

        converters.put(boolean.class, new BooleanConverter());
        converters.put(Boolean.class, new BooleanConverter());

        converters.put(int.class, new IntegerConverter());
        converters.put(Integer.class, new IntegerConverter());

        converters.put(long.class, new LongConverter());
        converters.put(Long.class, new LongConverter());

        converters.put(float.class, new FloatConverter());
        converters.put(Float.class, new FloatConverter());

        converters.put(double.class, new DoubleConverter());
        converters.put(Double.class, new DoubleConverter());

        converters.put(BigInteger.class, new BigIntegerConverter());
        converters.put(BigDecimal.class, new BigDecimalConverter());

        converters.put(LocalDate.class, new LocalDateConverter());
        converters.put(LocalDateTime.class, new LocalDateTimeConverter());

        return converters;
    }

    protected abstract void addConverters(Map<Class<?>, Converter<?>> converters);

    public final Converter<?> getConverter(Class<?> clazz) {
        return converters.get(clazz);
    }
}
