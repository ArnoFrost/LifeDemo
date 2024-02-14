package com.arno.demo.life.work.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;

import com.arno.demo.life.base.utils.ImageUtils;

import java.util.WeakHashMap;

/**
 * 推送动效截屏图片管理类
 */
public class PushBitmapCacheManager {
    private static volatile PushBitmapCacheManager INSTANCE;
    private final WeakHashMap<String, Bitmap> bitmapCache;

    private PushBitmapCacheManager() {
        //初始化缓存数据
        bitmapCache = new WeakHashMap<>();
    }

    public static PushBitmapCacheManager getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        if (INSTANCE == null) {
            synchronized (PushBitmapCacheManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PushBitmapCacheManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 保存view截图
     *
     * @param key
     * @param view
     * @return
     */
    public boolean putBitmapByScale(@NonNull String key, @NonNull View view) {
        Bitmap bitmap = ImageUtils.getBitmapFromView(view);
        if (bitmap != null) {
            return putBitmapByScale(key, bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, true);
        } else {
            return false;
        }
    }

    /**
     * 压缩并放入存储
     *
     * @param key
     * @param srcBitmap
     * @param width     压缩宽度
     * @param height    压缩高度
     * @param isFilter  是否开启双线性过滤
     * @return
     */
    public boolean putBitmapByScale(@NonNull String key, @NonNull Bitmap srcBitmap, int width, int height, boolean isFilter) {
        if (width <= 0 || height <= 0) {
            return false;
        }
        if (bitmapCache != null) {
            //压缩图片
            Bitmap dstBitmap = Bitmap.createScaledBitmap(srcBitmap, width, height, isFilter);
            //防止是相同分辨率回收错误
            if (dstBitmap != srcBitmap) {
                srcBitmap.recycle();
            }
            //防止是同一个分辨率的引用 避免优化回收
            if (dstBitmap != null) {
                bitmapCache.put(key, dstBitmap);
            }
            return true;
        }
        return false;
    }

    /**
     * 放入存储并尝试降低色彩空间
     *
     * @param key
     * @param bitmap
     * @return 是否成功
     */
    public boolean putBitmap(@NonNull String key, @NonNull Bitmap bitmap) {
        if (bitmapCache != null) {
            //降低色彩空间
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                    && bitmap.getConfig() != Bitmap.Config.RGB_565) {
                bitmapCache.put(key, convert(bitmap, Bitmap.Config.RGB_565));
            } else {
                bitmapCache.put(key, bitmap);
            }
            return true;
        }
        return false;
    }

    /**
     * 获得bitmap 且不删除
     *
     * @param key
     * @return
     */
    public Bitmap getBitmap(@NonNull String key) {
        return getBitmapAndDelete(key, false);
    }

    /**
     * 获得Bitmap 且删除
     *
     * @param key
     * @param isDelete
     * @return
     */
    public Bitmap getBitmapAndDelete(@NonNull String key, boolean isDelete) {
        if (bitmapCache == null) {
            return null;
        }
        if (isDelete) {
            return bitmapCache.remove(key);
        } else {
            return bitmapCache.get(key);
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (bitmapCache != null) {
            bitmapCache.clear();
        }
        INSTANCE = null;
    }

    /**
     * 转换bitmap 色彩空间
     *
     * @param bitmap
     * @param config
     * @return
     */
    public Bitmap convert(@NonNull Bitmap bitmap, @NonNull Bitmap.Config config) {
        Bitmap convertedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        Canvas canvas = new Canvas(convertedBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (bitmap != convertedBitmap) {
            bitmap.recycle();
        }
        return convertedBitmap;
    }
}
