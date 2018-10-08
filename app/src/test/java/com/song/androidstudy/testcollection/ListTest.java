package com.song.androidstudy.testcollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTest {

    @Test
    public void test_list() {

        // 1. list集合继承AbstractList->AbstractCollection->Collection
        // 2. list集合是非线程安全的，如果不在外部处理，需要Collections.synchronizedList(list)封装
        // 3. arraylist底层数据结构为数组，之后不断增加长度
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(list.size());
        System.out.println(list.get(0));
        list.remove(0);
        System.out.println(list.toString());
        System.out.println(list);

        List<Integer> objects = Collections.synchronizedList(list);

    }
}
