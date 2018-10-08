package com.song.androidstudy.testcollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionTest {

    /**
     * 集合增删改查
     */
    @Test
    public void test_aduq() {

        Collection<Integer> collection = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            collection.add(i);
        }
        System.out.println("contains-->" + collection.contains(3));
        System.out.println("remove-->" + collection.remove(3));
        System.out.println("size-->" + collection.size());
        System.out.println("toString-->" + collection.toString());
        System.out.println("toArray-->" + collection.toArray());

        Collection<Integer> collectionTwo = new ArrayList<>();
        collectionTwo.add(-1);
        System.out.println("addAll-->" + collection.addAll(collectionTwo));
        System.out.println("collection-->" + collection);

        // collection遍历
        Iterator<Integer> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // 增强for循环
        for (Integer i : collection) {
            System.out.println(i);
        }
    }

    /**
     * 容器之间的集成关系
     */
    @Test
    protected void test_collection(){
        /**
         * 1. 顶级接口分别为 Iterator、Collection、Map
         * 2. Iterator 子类为ListIterator和Collection
         * 3. Collection子类为AbstractCollection、List、Set、Queue, Map集合并非Collection子类，但是有依赖关系
         * 4. List 子类为AbstractList，AbstractList子类有Vector（子类为Stack）、Stack、ArrayList、AbstractSequentialList（子类为LinkedList）、LinkedList
         * 5. List核心为ArrayList和LinkedList，另外的Vector和Stack不常用
         * 6. ArrayList数据结构为数组、非线程安全的、每次自动扩充容量；LinkedList数据结构为双向链表，非线程安全，增删效率高
         * 7. Set集合子类有AbstractSet、SortedSet，其中AbstractSet也继承AbstractCollection，同理List的子类AbstractList也集成AbstractCollection
         * 8. AbstractSet子类有HashSet（子类LinkedHashSet）、TreeSet（另外父类SortedSet）、LinkedHashSet
         * 9. Set中核心的为HashSet、LinkedHashSet，TreeSet。数据结构分别为Hash、双向链表、二叉树（红黑树），其中TreeSet有顺序
         * 10. Map集合子类有AbstractMap、SortedMap子类（TreeMap），AbstractMap有HashMap（子类LinkedHashMap）、HashTable、TreeMap、WeakHashMap
         * 11. Map核心类为HashMap、LinkedHashMap、TreeMap
         * 12. 工具类Arrays和Collections
         * 13. 1.0/1.1版本集合类,Vector、Stack、Hashtable都是线程安全的，效率很低
         */

        // 所以set和list的增删改查差不多，且都有迭代器，Map较为特殊，和Collection是依赖关系


    }
}
