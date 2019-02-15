package com.song.androidstudy.rxjava;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * rxjava2 操作符
 * <p>
 * Created by chensongsong on 2018/11/17.
 */
public class RxJavaOperatorTest {

    /**
     * 测试create操作符
     */
    @Test
    public void test_create() {

        // ObservableOnSubscribe被存储在Observable
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                // 依次调用
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                // 事件回调
                System.out.println(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                // 异常回调
                System.out.println(throwable.getMessage());

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                // onComplete回调
                System.out.println("Action");

            }
        });

    }

    @Test
    public void test_just() {

        Observable.just(1, 2, 3, 7, 8)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        // 接受参数
                        System.out.println(integer);
                    }
                });

    }

    @Test
    public void test_from() {

        Observable.fromArray(new String[]{"1", "s", "f"})
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        System.out.println(Thread.currentThread().getName() + "-->" + s);
                        return s;
                    }
                })
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable.getMessage());

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println("Action");
                    }
                });

    }

}
