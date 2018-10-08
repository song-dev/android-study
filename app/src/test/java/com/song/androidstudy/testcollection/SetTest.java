package com.song.androidstudy.testcollection;

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetTest {

    /**
     * 1. set内部为不重复数据，当插入重复返回false
     * 2. 因为set元素必须是唯一的，所以必须定义equal方法确保元素的唯一性
     * 3. HashSet、TreeSet、LinkedHashSet都是非线程安全的，若要实现线程安全，Collections.synchronizedSet()处理
     */
    @Test
    public void test_set() {

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }
        System.out.println(set);
        System.out.println(set.add(-1));
        System.out.println(set.add(-2));
        System.out.println(set.add(-3));
        System.out.println(set.add(5));
        System.out.println(set);
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next()+" ");
        }
        while (iterator.hasNext()){
            iterator.remove();
        }
        System.out.println();
        System.out.println(set.size());
    }

    /**
     * 1. hashset对查找优化，默认选择
     * 2. hashset是无序的，底层数据结构为hash表
     * 3. 内部实现为HashMap，key为传入的元素
     */
    @Test
    public void test_hashset() {
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            hashSet.add(i);
        }
        hashSet.add(-11);
        hashSet.add(-1);
        System.out.println(hashSet);
        System.out.println(hashSet.contains(1));
        System.out.println(hashSet.remove(1));
        System.out.println(hashSet.size());
        System.out.println(hashSet);
        HashSet<Integer> testHashSet = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            testHashSet.add(i);
        }
        System.out.println(hashSet.containsAll(testHashSet));
        System.out.println(hashSet.addAll(testHashSet));
        System.out.println(hashSet.containsAll(testHashSet));

        System.out.println(hashSet);
    }

    /**
     * 1. 内部数据结构红黑树
     * 2. 存储默认升序，继承sortedset
     * 3. 插入元素必须实现Comparable
     * 4. 内部实现为TreeMap，其中key为元素，值为默认相同的Object对象
     */
    @Test
    public void test_treeset(){
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(3);
        treeSet.add(5);
        treeSet.add(0);
        treeSet.add(9);
        treeSet.add(7);
        treeSet.add(6);
        treeSet.add(2);
        treeSet.add(4);
        treeSet.add(8);
        System.out.println(treeSet);
    }

    /**
     * 1. 父类为HashSet，内部数据结构为双向链表
     * 2. 默认链接哈希集初始容量为16
     * 3. 内部实现为LinkedHashMap，默认key为元素值，value为默认相同的Object对象
     * 4. 默认顺序为插入元素顺序
     */
    @Test
    public void test_linkedhashset(){
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(1);
        linkedHashSet.add(3);
        linkedHashSet.add(5);
        linkedHashSet.add(0);
        linkedHashSet.add(9);
        linkedHashSet.add(7);
        linkedHashSet.add(6);
        linkedHashSet.add(2);
        linkedHashSet.add(4);
        linkedHashSet.add(8);
        System.out.println(linkedHashSet);
    }
}
