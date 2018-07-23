package com.jigsawcorp.android.jigsaw.View.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class CategoryIndicatorCircle extends View {

    private Rect rectangle;
    private Paint paint;




    Paint mPaint = null;

    public CategoryIndicatorCircle(Context context) {
        super(context);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        int radius;
        radius = 15;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        //canvas.drawPaint(mPaint);
        // Use Color.parseColor to define HTML colors
       // mPaint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawCircle(x /2, y / 2, radius, mPaint);
    }


}
