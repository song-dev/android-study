package com.song.androidstudy.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.song.androidstudy.R;
import com.song.androidstudy.crypto.HashUtils;
import com.song.androidstudy.utils.HttpUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图片的缓存
 * <p>
 * Created by chensongsong on 2018/10/28.
 */
public class BitmapActivity extends AppCompatActivity {

    private static final String TAG = "BitmapActivity";
    @BindView(R.id.imageView)
    ImageView imageView_main_show;
    private LruCache<String, Bitmap> lruCache = null;
    private Map<String, SoftReference<Bitmap>> softMap = new HashMap<String, SoftReference<Bitmap>>();
    ;
    private String urlString = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
    private Handler handler = new Handler();

    private DiskLruCache diskLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);

        Log.e(TAG, "onCreate: " + Environment.getDataDirectory().getAbsolutePath());
        Log.e(TAG, "onCreate: " + getFilesDir());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e(TAG, "onCreate: " + getDataDir());
        }

        // 获取应用剩余的内存空间
        int memoryCount = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = memoryCount / 8;
        // 本质上LruCache是一个强引用对象，但是必须要限制这个对象的大小
        lruCache = new MyLruCache(cacheSize);

        try {
            // 初始化
            diskLruCache = DiskLruCache.open(getDiskCacheDir(this, "bitmap"), 1, 1, 1024 * 1024 * 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 外部存储可用，就返回外部存储路径，若内部存储可用返回内部存储路径
     *
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 从diskLruCache获取
     */
    @OnClick(R.id.button2)
    public void disklrucache() {
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(HashUtils.sha1Encode(urlString));
            InputStream inputStream = snapshot.getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView_main_show.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.button)
    public void loadImageView() {
        // 从cache中获取，如果没有再从网络中获取
        Bitmap bm = getBitmapFromCache(urlString);
        if (bm != null) {
            imageView_main_show.setImageBitmap(bm);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "--->网络访问开始");
                    byte[] result = HttpUtils.requsetUrl(urlString);
                    if (result != null) {
                        Log.i(TAG, "--->网络访问：" + result.length);

                        try {
                            // 添加到缓存
                            DiskLruCache.Editor edit = diskLruCache.edit(HashUtils.sha1Encode(urlString));
                            OutputStream outputStream = edit.newOutputStream(0);
                            outputStream.write(result);
                            outputStream.flush();
                            outputStream.close();
                            edit.commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        final Bitmap bm = BitmapFactory.decodeByteArray(result, 0, result.length, options);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (bm != null) {
                                    Log.i(TAG, "--->网络访问：" + bm.toString());
                                    imageView_main_show.setImageBitmap(bm);
                                    lruCache.put(urlString, bm);
                                }
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void remove(String key) {
        try {
            diskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MyLruCache extends LruCache<String, Bitmap> {
        public MyLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            // 返回bitmap对象在内存当中实际的大小
            // return value.getWidth() * value.getHeight() * 4;
            return value.getRowBytes() * value.getHeight();
        }

        @Override
        protected void entryRemoved(boolean evicted, String key,
                                    Bitmap oldValue, Bitmap newValue) {
            // evicted，是否要移除，如果要就将oldValue放入弱引用，并且将其从强引用删除
            if (evicted) {
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(
                        oldValue);
                softMap.put(key, softReference);
                lruCache.remove(key);
            }
        }
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    private Bitmap getBitmapFromCache(String key) {
        Bitmap bm = null;
        bm = lruCache.get(key);
        if (bm != null) {
            // 如果存在强引用中，直接获取
            Log.i(TAG, "--->LruCache：" + bm.toString());
            return bm;
        } else {
            // 否则就在软引用当中找
            SoftReference<Bitmap> soft = softMap.get(key);
            if (soft != null) {
                bm = soft.get();
                if (bm != null) {
                    // 如果存在软引用当中，put到强引用中，并且从软引用当中移除
                    lruCache.put(key, bm);
                    softMap.remove(key);
                    Log.i(TAG, "--->softMap：" + bm.toString());
                    return bm;
                }
            }
        }
        return null;
    }

    /**
     * 计算insampleSize，因为insampleSize只能为2的倍数，故只缩放处理
     *
     * @param options
     * @param reqHeight 目标高
     * @param reqWidth  目标宽
     * @return
     */
    private int calculateInSampeSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {

        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        // 默认不缩放
        int insampleSize = 1;
        if (outWidth > reqWidth || outHeight > reqHeight) {
            // 只有大于目标大小才缩放处理
            int halfWidth = outWidth / 2;
            int halfHeight = outHeight / 2;
            while (halfHeight / insampleSize >= reqHeight && halfWidth / insampleSize >= reqWidth) {
                insampleSize *= 2;
            }
        }
        return insampleSize;
    }

    /**
     * 二次采样加载图片
     *
     * @param filePath
     * @param newHeight 目标高
     * @param newWidth  目标宽
     * @return
     */
    private Bitmap createImageThumbnail(String filePath, int newHeight, int newWidth) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 仅仅解码边界
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int oldHeight = options.outHeight;
        int oldWidth = options.outWidth;
        Log.i(TAG, "高度是：" + oldHeight + "，宽度是：" + oldWidth);
        int ratioHeight = oldHeight / newHeight;
        int ratioWidth = oldWidth / newWidth;
        options.inSampleSize = ratioHeight > ratioWidth ? ratioWidth : ratioHeight;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        Log.i(TAG, "高度是：" + options.outHeight + "，宽度是：" + options.outWidth);
        return bm;


    }


}
