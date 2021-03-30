package com.arno.demo.life.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

/**
 * Image工具类
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";

    public static Bitmap getBmpFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        // 像StateListDrawable和 LevelListDrawable等集合Drawable对象，需要getCurrent()才能取出真正使用中的Drawable对象
        while (drawable.getCurrent() != drawable) {
            drawable = drawable.getCurrent();
        }

        // 对于BitmapDrawable及其派生类，直接取就行了
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // 对于其他的Drawable，需要通过Canvas画出一个bitmap
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                        : Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap getBmpFromImageView(ImageView imv) {
        if (imv == null) {
            return null;
        }

        return getBmpFromDrawable(imv.getDrawable());
    }

    @Deprecated
    public static Bitmap getBitmapFromView(Context ctx, View view) {
        return getBitmapFromView(view);
    }

    /**
     * @param view         从View获取Bitmap图像，可选择裁剪范围
     * @param leftOffset   左侧裁剪的数值
     * @param topOffset    上方裁剪的数值
     * @param rightOffset  右方裁剪的数值
     * @param bottomOffset 下方裁剪的数值
     * @return
     */
    public static Bitmap getBitmapFromView(View view, int leftOffset, int topOffset, int rightOffset, int bottomOffset) {
        Bitmap bitmap = null;
        Bitmap newBitmap = null;
        try {
            boolean state = view.isDrawingCacheEnabled(); //记录原状态，用后复原设置
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            bitmap = view.getDrawingCache();
            newBitmap = Bitmap.createBitmap(bitmap, leftOffset, topOffset, view.getMeasuredWidth() - leftOffset - rightOffset, view
                    .getMeasuredHeight() - topOffset - bottomOffset);
            bitmap.recycle();
            view.setDrawingCacheEnabled(state); //恢复到view原本的设置
            view.destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBitmap == null ? bitmap : newBitmap;
    }

    /**
     * WebView截长图
     */
    public static Bitmap captureWebViewLongPic(WebView view, int leftOffset, int topOffset, int rightOffset, int captureHeight) {
        Bitmap bitmap = null;
        Bitmap newBitmap = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //记录原状态，用后复原设置
                boolean state = view.isDrawingCacheEnabled();
                view.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
                int width = view.getMeasuredWidth() - leftOffset - rightOffset;
                newBitmap = Bitmap.createBitmap(bitmap, leftOffset, topOffset, width, captureHeight);
                bitmap.recycle();
                //恢复到view原本的设置
                view.setDrawingCacheEnabled(state);
                view.destroyDrawingCache();
            } else {
                //获取Picture对象
                Picture picture = view.capturePicture();
                //得到图片的宽和高
                int pictureWidth = picture.getWidth();
                int pictureHeight = picture.getHeight();
                if (pictureWidth > 0 && pictureHeight > 0) {
                    //创建位图
                    bitmap = Bitmap.createBitmap(pictureWidth, pictureHeight, Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    //绘制(会调用native方法，完成图形绘制)
                    picture.draw(canvas);
                    int width = pictureWidth - leftOffset - rightOffset;
                    newBitmap = Bitmap.createBitmap(bitmap, leftOffset, topOffset, width, captureHeight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "captureWebViewLongPic Exception:" + e.getMessage());
        } catch (Error error) {
            error.printStackTrace();
            Log.d(TAG, "captureWebViewLongPic Error:" + error.getMessage());
        }
        return newBitmap == null ? bitmap : newBitmap;
    }

    public static Bitmap getBitmapFromView(View view) {
        Rect rect = new Rect();
        view.getDrawingRect(rect);

        Bitmap bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        view.draw(canvas);

        return bitmap;
    }

    /**
     * 生成与目标Bitmap 相同大小的Bitmap
     *
     * @param src  目标Bitmap
     * @param mark 要操作的Bitmap
     * @return
     */
    public static Bitmap getMaskBitmap(Bitmap src, Bitmap mark) {
        if (src == null || mark == null) {
            return null;
        }
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int markWidth = mark.getWidth();
        int markHeight = mark.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = (float) srcWidth / markWidth;
        float scaleHeight = (float) srcHeight / markHeight;
        matrix.postScale(scaleWidth, scaleHeight);
        mark = Bitmap.createBitmap(mark, 0, 0, markWidth, markHeight, matrix, true);
        return mark;
    }

    /**
     * 将Bitmap绘制到Bitmap上
     *
     * @param src  源Bitmap
     * @param mark 要绘制上的Bitmap
     * @return
     */
    public static Bitmap createMaskBitmap(Bitmap src, Bitmap mark) {
        if (src == null || mark == null) {
            return null;
        }
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();

        //创建一个bitmap
        Bitmap newBitmap = Bitmap.createBitmap(srcWidth, srcHeight, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newBitmap);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(mark, 0, 0, null);
        // 保存
//        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        // 存储
        canvas.restore();
        return newBitmap;
    }

    public static Bitmap getRoundBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        bitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - size) / 2,
                (bitmap.getHeight() - size) / 2, size, size);

        Bitmap output = Bitmap.createBitmap(size, size, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        RectF rect = new RectF(0, 0, size, size);
        int radius = size / 2;
        canvas.drawRoundRect(rect, radius, radius, paint);
        return output;
    }

    /**
     * 获取制定倒角的圆角图片
     *
     * @param bitmap
     * @param radius 倒角
     * @return
     * @author libin8
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int radius) {
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        bitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - size) / 2,
                (bitmap.getHeight() - size) / 2, size, size);
        Bitmap output = Bitmap.createBitmap(size, size, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        RectF rect = new RectF(0, 0, size, size);
        radius = (radius > size / 2) ? size / 2 : radius;
        canvas.drawRoundRect(rect, radius, radius, paint);
        return output;
    }

    /**
     * 圆角边框 图片
     *
     * @param bitmap
     * @param viewWidth
     * @param viewHeight
     * @param radius     边角角度
     * @param boarder    边框宽度
     * @param color      边框颜色
     * @return
     */
    public static Bitmap getRoundBitmapByShader(Bitmap bitmap
            , int viewWidth, int viewHeight, int radius, int boarder, int color) {
        if (bitmap == null) {
            return null;
        }
        if (viewWidth <= 0 || viewHeight <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float widthScale = viewWidth * 1f / width;
        float heightScale = viewHeight * 1f / height;

        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        //创建输出的bitmap
        Bitmap desBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Config.ARGB_8888);
        //创建canvas并传入desBitmap，这样绘制的内容都会在desBitmap上
        Canvas canvas = new Canvas(desBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //创建着色器
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //给着色器配置matrix
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        //创建矩形区域并且预留出border
        RectF rect = new RectF(boarder, boarder, viewWidth - boarder, viewHeight - boarder);
        //把传入的bitmap绘制到圆角矩形区域内
        canvas.drawRoundRect(rect, radius, radius, paint);

        if (boarder > 0) {
            //绘制boarder
            Paint boarderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            boarderPaint.setColor(color);
            boarderPaint.setStyle(Paint.Style.STROKE);
            boarderPaint.setStrokeWidth(boarder);
            canvas.drawRoundRect(rect, radius, radius, boarderPaint);
        }
        return desBitmap;
    }

    public static Bitmap getRectangleRoundBitmap(Bitmap bitmap, int radius) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        RectF rect = new RectF(0, 0, width, height);
        radius = (radius > size / 2) ? size / 2 : radius;
        canvas.drawRoundRect(rect, radius, radius, paint);
        return output;
    }

    public static Drawable getCircleDrawable(Application application, Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        Resources res = application.getResources();

        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = BitmapDrawable.class.cast(drawable).getBitmap();
            drawable = new BitmapDrawable(res, getRoundBitmap(bitmap));
        }

        return drawable;
    }

    /**
     * Method allows to find out the scale which can be applied when loading image in order to save memory
     *
     * @param options   preset and preloaded option of an image
     * @param reqWidth  required width of image
     * @param reqHeight required height of image
     * @return scaling of image to be applied while loading the image (as power of 2)
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "<Image> reqWidth:" + reqWidth + ", reqHeight:" + reqHeight + ", option.width:" + width + ", option.height:" + height + ", inSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    public static Bitmap getBitmapFromLocDir(String bitmapPath, int maxSize) {
        if (TextUtils.isEmpty(bitmapPath)) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(bitmapPath, options);
            options.inSampleSize = calculateInSampleSize(options, maxSize, maxSize);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(bitmapPath, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param bitmap
     * @param roundPixels
     * @return 左上，左下带圆角的bitmap，异常时返回原图
     */
    public static Bitmap getLeftRoundBitmap(Bitmap bitmap, int roundPixels) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Bitmap roundCornerImage = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(roundCornerImage);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, width, height);
            RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, null, rect, paint);
            return Bitmap.createBitmap(roundCornerImage, 0, 0, width - roundPixels, height);
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }
}
