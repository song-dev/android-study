package com.song.androidstudy.rxjava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
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

    /**
     * 线程调度器Schedulers测试
     */
    @Test
    public void test_from() {

        Observable.fromArray(new String[]{"1", "s", "f"})
                // 控制发射数据线程
                .subscribeOn(Schedulers.io())
                // 控制准备工作
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("prepare-->" + Thread.currentThread().getName());
                    }
                })
                // 控制准备工作线程
                .subscribeOn(Schedulers.single())
                // 控制map操作线程
                .observeOn(Schedulers.newThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        System.out.println(Thread.currentThread().getName() + "-->" + s);
                        return s;
                    }
                })
                // 控制map操作线程
                .observeOn(Schedulers.io())
                // 当前线程调度器无效, 除非调度准备线程，否则subscribeOn只能调用一次（多调用无效无效）
                .subscribeOn(Schedulers.newThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        System.out.println("two map-->" + Thread.currentThread().getName() + "-->" + s);
                        return s;
                    }
                })
                // 控制subscriber回调线程
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

    /**
     * 转换操作符控制
     */
    @Test
    public void test_operator() throws Exception{

        // flatmap
        Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        System.out.println("flatMap-->" + s);
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("i am value " + s);
                        }
                        return Observable.fromIterable(list).delay(10, TimeUnit.SECONDS);
                    }
                })
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });

        // concatMap
        Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        System.out.println("concatMap-->" + s);
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("i am value " + s);
                        }
                        return Observable.fromIterable(list).delay(10, TimeUnit.SECONDS);
                    }
                })
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });

        // 延迟操作
        Thread.sleep(1000);

    }


}
