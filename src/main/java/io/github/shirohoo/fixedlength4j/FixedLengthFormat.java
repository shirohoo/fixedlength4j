package io.github.shirohoo.fixedlength4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FixedLengthFormat<T> {
    private final Class<T> clazz;

    private final ConverterRegistrar converterRegistrar;

    public FixedLengthFormat(Class<T> clazz) {
        this(clazz, new SimpleConverterRegistrar());
    }

    public FixedLengthFormat(Class<T> clazz, ConverterRegistrar converterRegistrar) {
        if (clazz == null) {
            throw new IllegalArgumentException("'clazz' must not be null");
        }
        if (converterRegistrar == null) {
            throw new IllegalArgumentException("'converterRegistrar' must not be null");
        }

        this.clazz = clazz;
        this.converterRegistrar = converterRegistrar;
    }

    public ByteBuffer serialize(List<T> objects) {
        if (objects == null) {
            throw new IllegalArgumentException("'objects' must not be null.");
        }

        if (objects.isEmpty()) {
            return ByteBuffer.allocate(0);
        }

        return objects.stream()
                .reduce(
                        ByteBuffer.allocate(this.getTotalBytes() * objects.size()),
                        (buffer, object) -> buffer.put(this.serialize(object)),
                        ByteBuffer::put);
    }

    @SuppressWarnings("unchecked")
    public ByteBuffer serialize(T object) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.getTotalBytes());
        int bufferPosition = 0;

        for (Field declaredField : clazz.getDeclaredFields()) {
            try {
                Converter<Object> converter =
                        (Converter<Object>) converterRegistrar.getConverter(declaredField.getType());
                Fixed annotation = declaredField.getAnnotation(Fixed.class);
                Object rowValue = this.findGetter(declaredField).invoke(object);
                String parsedValue = converter.convertToString(rowValue, annotation);

                byte[] bytes = parsedValue.getBytes(); // Encoding for multibyte characters.
                byteBuffer.position(bufferPosition);
                byteBuffer.put(bytes);
                bufferPosition += bytes.length; // Increment the buffer position by the number of bytes written.

            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

        return (ByteBuffer) byteBuffer.flip();
    }

    @SuppressWarnings("unchecked")
    public List<T> deserialize(ByteBuffer byteBuffer) {
        int capacity = byteBuffer.limit() / this.getTotalBytes();
        List<T> objects = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            T object = this.newInstance();

            for (Field declaredField : clazz.getDeclaredFields()) {
                try {
                    Converter<Object> converter =
                            (Converter<Object>) converterRegistrar.getConverter(declaredField.getType());
                    Fixed annotation = declaredField.getAnnotation(Fixed.class);
                    String rowValue = this.readBuffer(byteBuffer, annotation.bytes());
                    Object parsedValue = converter.convertToObject(rowValue, annotation);

                    this.findSetter(declaredField).invoke(object, parsedValue);

                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }

            objects.add(object);
        }

        return objects;
    }

    private int getTotalBytes() {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> field.getAnnotation(Fixed.class))
                .map(Fixed::bytes)
                .reduce(0, Integer::sum);
    }

    private Method findGetter(Field declaredField) {
        try {
            return clazz.getMethod(
                    "get" + declaredField.getName().substring(0, 1).toUpperCase()
                            + declaredField.getName().substring(1));
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("No such getter for '" + declaredField.getName() + "'.", e);
        }
    }

    private Method findSetter(Field declaredField) {
        try {
            return clazz.getMethod(
                    "set" + declaredField.getName().substring(0, 1).toUpperCase()
                            + declaredField.getName().substring(1),
                    declaredField.getType());
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("No such setter for '" + declaredField.getName() + "'.", e);
        }
    }

    private T newInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("No such default constructor in '" + clazz.getName() + "'.", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private String readBuffer(ByteBuffer byteBuffer, int bytes) {
        byte[] array = new byte[bytes];
        for (int i = 0; i < bytes; i++) {
            array[i] = byteBuffer.get();
        }
        return new String(array);
    }
}
