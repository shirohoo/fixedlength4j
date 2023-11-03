# Fixed Length Format for Java

## Table of Contents
1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [Usage](#usage)
4. [Customization](#customization)
    1. [Byte Order](#byte-order)
    2. [Converters](#converters)

---

## Introduction
This project is a fast and lightweight, zero-dependency nio-based pure Java 8+ library with the goal of converting Fixed Length Format files into Java classes and performing the reverse operation.

I used the lowest version of Java that is realistically available because it is primarily used in the financial sector.

## Prerequisites
* Java 8+
* IDE (IntelliJ, Eclipse, etc.)

## Usage
For example, Fixed Length Format means a file in the following format (here 33 byte * 2).

```text
John      Doe       0301993-01-01Michael   Andrew    0331990-01-01
```

Here, the first 10 bytes represent the first name, the next 10 bytes represent the last name, the next 3 bytes represent the age, and the next 10 bytes represent the birthday. 

You can easily convert these data to Java classes, and of course you can also convert Java classes to Fixed Length Format. At this time, `java.nio.ByteBuffer` is used primarily for all conversions.

First, create the following class.

```java
public class Employee {
    @Fixed(bytes = 10)
    private String firstName;

    @Fixed(bytes = 10)
    private String lastName;

    @Fixed(bytes = 3, pad = Pad.ZERO, align = Align.RIGHT)
    private int age;

    @Fixed(bytes = 10, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
```

And you can convert the Fixed Length Format data to a Java class using the `FixedLengthFormat` class as follows.

```java
ByteBuffer buffer = ByteBuffer.wrap("John      Doe       0301993-01-01Michael   Andrew    0331990-01-01".getBytes());
List<Employee> employees = new FixedLengthFormat<>(Employee.class).deserialize(buffer);
```

If you want to do the opposite, you can write code like this:

```java
List<Employee> employees = new ArrayList<>();
employees.add(new Employee("John", "Doe", 30, LocalDate.of(1993, 1, 1)));
employees.add(new Employee("Michael", "Andrew", 33, LocalDate.of(1990, 1, 1)));
ByteBuffer buffer = new FixedLengthFormat<>(Employee.class).serialize(employees);
```

## Customization

### Byte Order

Java's byte order is basically `big-endian`. Since network byte order is the same, there is usually no need to change it. 
However, if you are using `little-endian` based hardware and need to access the hardware directly, you can very easy change the byte order by modifying the `ByteOrder` of a `java.nio.ByteBuffer`.

```java
ByteBuffer byteBuffer = ByteBuffer.allocate(10);
byteBuffer.order(ByteOrder.BIG_ENDIAN);
```

### Converters

Basically implemented converters support the following types.

- String
- boolean, Boolean 
- int, Integer
- long, Long
- float, Float
- double, Double
- BigInteger
- BigDecimal
- LocalDate
- LocalDateTime

If you need an additional converter, you can implement `Converter` or `AbstractConverter` and register the converter yourself.
it is more convenient to extend `AbstractConverter` than to implement this interface directly.

Once you have implemented `Converter`, it is time to implement and register `ConverterRegistrar`. 
Just register the `Converter` you implemented when inheriting `ConverterRegistrar` and implementing `protected abstract void addConverters(Map<Class<?>, Converter<?>> converters)`. 

```java
final class MyConverterRegistrar extends ConverterRegistrar {
    @Override
    protected void addConverters(Map<Class<?>, Converter<?>> converters) {
        converters.put(MyType.class, new MyTypeConverter());
    }
}
```

If you have implemented `ConverterRegistrar` as above, you can also pass the implemented `ConverterRegistrar` to the constructor of the `FixedLengthFormat` class.

For example:

```java
List<Employee> employees = new FixedLengthFormat<>(Employee.class, new MyConverterRegistrar()).deserialize(buffer);
```
