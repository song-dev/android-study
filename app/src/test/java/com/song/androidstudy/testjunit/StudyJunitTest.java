package com.song.androidstudy.testjunit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 1. 查看日志需要用debug模式运行
 * 2. 导入junit:junit:4.12框架
 */
public class StudyJunitTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试基本数据类型
     */
    @Test
    public void testBaseDataType(){
        System.out.println("StudyJunitTest-->testBaseDataType");
        Assert.assertEquals(1,1);
    }

    /**
     * 测试基本布尔类型
     */
    @Test
    public void testBooble(){
        System.out.println("StudyJunitTest-->testBooble");
        Assert.assertFalse(false);
    }
}