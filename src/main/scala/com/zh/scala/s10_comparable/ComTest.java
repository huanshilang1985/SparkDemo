package com.zh.scala.s10_comparable;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/14 21:04
 */
public class ComTest {

    public static void main(String[] args) {
        Person1 p1 = new Person1("zhanghe", 18);
        Person1 p2 = new Person1("laosan", 19);

        List<Person1> list = new ArrayList<>();
        list.add(p2);
        list.add(p1);

        Collections.sort(list);

        for(Person1 p : list){
            System.out.println(p.getName() + " " + p.getAge());
        }

    }

}
