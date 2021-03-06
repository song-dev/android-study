package com.song.androidstudy.thread;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.song.androidstudy.R;
import com.song.androidstudy.utils.HttpUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chensongsong on 2018/10/28.
 */
public class ThreadActivity extends AppCompatActivity {

    private static final String TAG = "ThreadActivity";

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        bind = ButterKnife.bind(this);


    }

    @OnClick(R.id.asynctask)
    public void asynctask() {
        // 开启async
        new TestAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://e.hiphotos.baidu.com/image/pic/item/48540923dd54564eaebe8b5dbede9c82d0584ffe.jpg");
        // s说明是异步执行任务
        new TestAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://e.hiphotos.baidu.com/image/pic/item/48540923dd54564eaebe8b5dbede9c82d0584ffe.jpg");

        new Thread(new Runnable() {
            @Override
            public void run() {
//                new TestAsyncTask().execute("http://e.hiphotos.baidu.com/image/pic/item/48540923dd54564eaebe8b5dbede9c82d0584ffe.jpg");
                new TestAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://e.hiphotos.baidu.com/image/pic/item/48540923dd54564eaebe8b5dbede9c82d0584ffe.jpg");
            }
        }).start();
    }

    @OnClick(R.id.intentservice)
    public void intentservice() {
        // 启动IntentService
        startService(new Intent(this, TestIntentService.class));
    }

    @OnClick(R.id.count_down_latch)
    public void testCountDownLatch() {
        new CountDownLatchAsync().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * api 9以上为并行，api 16以上为串行，当前默认为并行。
     * ThreadPoolExecutor 线程池并行
     */
    private class TestAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            // 主线程准备工作
            super.onPreExecute();
            Log.e(TAG, "onPreExecute: " + Thread.currentThread().getName());
        }

        @Override
        protected String doInBackground(String... strings) {
            // 子线程
            publishProgress(0);
            String s = HttpUtils.requestGet(strings[0]);
            publishProgress(100);
            Log.e(TAG, "doInBackground: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // 主线程更新
            Log.e(TAG, "onProgressUpdate: " + values[0] + Thread.currentThread().getName());
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            // 回调主线程
            super.onPostExecute(s);
            Log.e(TAG, "onPostExecute: " + Thread.currentThread().getName());
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    private class CountDownLatchAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            new TestCountDownLatch().test();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
