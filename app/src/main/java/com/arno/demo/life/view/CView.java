package com.arno.demo.life.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.arno.demo.life.R;


public class CView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private int padding = 0;

    public CView(Context context) {
        super(context);
    }

    public CView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    public CView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sina_cover);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 2; i++) {
            canvas.save();
            //region 使用clipRect来优化布局
            Rect rect = new Rect(padding, 0, 100 + padding, bitmap.getWidth() + padding);
            RectF rectf = new RectF(0, 0, 100, 0);
            Log.d("Arno", "quickReject : " + canvas.quickReject(rectf, Canvas.EdgeType.BW));
            canvas.clipRect(rect);
            //endregion
            canvas.drawBitmap(bitmap, padding, 0, paint);

            canvas.restore();
            padding += 100;
        }
        canvas.drawBitmap(bitmap, padding, 0, paint);
    }
}
