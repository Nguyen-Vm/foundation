package org.linker.foundation.JavaBean;

import java.util.Date;

/**
 * @author RWM
 * @date 2018/8/25
 */
public class User {

    public String id;

    public String name;

    public Integer age;

    public Date birthday;

    public User(String id, String name, Integer age, Date birthday) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
