package com.song.androidstudy.testgc;

import org.junit.Test;

/**
 * 测试堆上 GC
 * Created by chensongsong on 2019/9/9.
 */
public class TestHeapGc {

    private final static int _1MB = 1024 * 1024;

    /**
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M XX:+PrintGCDetails -XX:+SurvivorRation=8
     * 测试新生代 GC
     */
    @Test
    public void test_minor_gc() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        // 出现一次 Minor gc
        allocation4 = new byte[4 * _1MB];

        System.gc();

    }

}
