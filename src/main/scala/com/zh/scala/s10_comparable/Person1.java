package com.zh.scala.s10_comparable;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/14 21:01
 */
public class Person1 implements Comparable<Person1> {

    private String name;
    private int age;

    @Override
    public int compareTo(Person1 o) {
        return this.age - o.age;  //升序
    }

    public Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
