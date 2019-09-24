package com.song.androidstudy.thread;

import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {

    private static final String TAG = "TestCountDownLatch";

    public void test(){


        Log.e(TAG, "TestCountDownLatch: start.");
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                countDownLatch.countDown();

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                countDownLatch.countDown();

            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "TestCountDownLatch: end.");


    }

}
