package com.song.androidstudy.testoom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 分别示例栈溢出，堆溢出，字符串常量池存储F
 * Created by chensongsong on 2019/9/2.
 */
public class LoopMethodTest {

    private int count = 0;

    private void baseMethod() {
        count++;
        baseMethod();
    }

    @Test
    public void test_loop() {

        try {
            baseMethod();
        } catch (Throwable throwable) {
            System.out.println("count = " + count);
            throwable.printStackTrace();
        }

    }

    @Test
    public void test_heap_oom() {

        try {
            ArrayList<Object> list = new ArrayList<>();
            while (true) {
                list.add(new Object());
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    static class OOMObject {
    }

    @Test
    public void test_method_area() {

        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invoke(obj, args);
                }
            });
            enhancer.create();
        }

    }

    @Test
    public void test_intern() {

        String s = new StringBuffer().append("haha").append("哈哈").toString();
        System.out.println(s == s.intern());

        String s2 = new StringBuffer().append("Ja").append("va").toString();
        System.out.println(s2 == s2.intern());

        System.out.println("test" == "test");


    }
}
