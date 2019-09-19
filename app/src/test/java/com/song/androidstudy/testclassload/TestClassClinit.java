package com.song.androidstudy.testclassload;

import org.junit.Test;

/**
 * 测试类加载的初始化
 * Created by chensongsong on 2019/9/12.
 */
public class TestClassClinit {

    @Test
    public void test_load() {

        // 被动使用类字段演示，通过子类引用父类静态字段，不会导致子类初始化
        System.out.println("子类引用父类静态字段：" + SubClass.value);

        // 被动引用演示，通过数组定义引用类，不会触发此类初始化。
        // 类加载准备阶段执行完成，即可知道分配的内存
        SuperClass[] list = new SuperClass[10];

        // 被动引用演示，编译阶段常量被存储在常量池中
        System.out.println(SubClass.TEST);

        // <clinit>() 方法执行顺序为父类静态语句块优先
        System.out.println(SubFather.b);
    }

}

class Father {

    public static int a = 1;

    static {
        // 父类静态语句块优先子类静态属性执行
        a = 2;
    }

}

class SubFather extends Father {

    public static int b = a;

}


class TestClassVariate {

    static {
        i = 13; // 给变量赋值正常编译通过
//         System.out.println(i); // illegal forward reference 非法向前引用
    }

    static int i = 12;

}

class SuperClass {

    static {
        System.out.println("SuperClass init.");
    }

    protected static int value = 12;

}

class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init.");
    }

    public final static String TEST = "hello world.";

}
