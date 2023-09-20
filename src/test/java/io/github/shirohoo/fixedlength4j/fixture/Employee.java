package io.github.shirohoo.fixedlength4j.fixture;

import io.github.shirohoo.fixedlength4j.Align;
import io.github.shirohoo.fixedlength4j.Fixed;
import io.github.shirohoo.fixedlength4j.Pad;
import java.time.LocalDate;

public class Employee {
    @Fixed(bytes = 10, pad = Pad.SPACE, align = Align.LEFT)
    private String firstName;

    @Fixed(bytes = 10, pad = Pad.SPACE, align = Align.LEFT)
    private String lastName;

    @Fixed(bytes = 3, pad = Pad.ZERO, align = Align.RIGHT)
    private int age;

    @Fixed(bytes = 10, pattern = "yyyy-MM-dd", pad = Pad.SPACE, align = Align.LEFT)
    private LocalDate birthday;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
