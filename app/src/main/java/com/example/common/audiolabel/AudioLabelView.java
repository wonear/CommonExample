package com.example.common.audiolabel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.common.R;
import com.wonear.common.utils.JsonUtil;
import com.wonear.common.utils.ViewUtil;
import com.wonear.common.widget.BaseCustomView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wonear
 * @date : 2019/12/3 16:22
 */
public class AudioLabelView extends BaseCustomView {

    private Paint bitmapPaint, labelPaint;
    private Bitmap mBitmap;
    private float mBmpWidth, mBmpHeight;

    private List<LabelModel> labelList;

    private LabelModel curLabel;

    public AudioLabelView(Context context) {
        super(context);
    }

    public AudioLabelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AudioLabelView);
            mBmpWidth = typedArray.getDimension(R.styleable.AudioLabelView_alv_imageWidth, getScreenWith(context));
            mBmpHeight = typedArray.getDimension(R.styleable.AudioLabelView_alv_imageHeight, getScreenHeight(context));
            Drawable imageSource = typedArray.getDrawable(R.styleable.AudioLabelView_alv_src);
            mBitmap = ViewUtil.drawableToBitmap(imageSource, mBmpWidth, mBmpHeight);
        }

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);

        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setColor(Color.YELLOW);

        String test ="[{\"audioPath\":\"dsd\",\"audioTime\":\"123456\",\"rect\":{\"bottom\":206,\"left\":134,\"right\":154,\"top\":186},\"x\":287,\"y\":392},{\"audioPath\":\"dsd\",\"audioTime\":\"123456\",\"rect\":{\"bottom\":236,\"left\":221,\"right\":241,\"top\":216},\"x\":461,\"y\":452},{\"audioPath\":\"dsd\",\"audioTime\":\"123456\",\"rect\":{\"bottom\":514,\"left\":692,\"right\":732,\"top\":474},\"x\":712,\"y\":494},{\"audioPath\":\"dsd\",\"audioTime\":\"123456\",\"rect\":{\"bottom\":1249,\"left\":437,\"right\":477,\"top\":1209},\"x\":457,\"y\":1229},{\"audioPath\":\"dsd\",\"audioTime\":\"123456\",\"rect\":{\"bottom\":400,\"left\":154,\"right\":174,\"top\":380},\"x\":328,\"y\":780},{\"audioPath\":\"dsd\",\"audioTime\":\"123456\",\"rect\":{\"bottom\":392,\"left\":48,\"right\":68,\"top\":372},\"x\":115,\"y\":763},{\"audioPath\":\"dsd\",\"audioTime\":\"123456\",\"rect\":{\"bottom\":301,\"left\":139,\"right\":159,\"top\":281},\"x\":297,\"y\":582}]";


        labelList = JsonUtil.jsonToList(test, LabelModel.class);
        if (labelList == null) labelList = new ArrayList<>();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        mBitmap = imageScale(mBitmap, mViewWidth, mViewHeight);
        Log.d("imageScale", mBitmap.getWidth() + "--" + mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, 0, 0, bitmapPaint);
        for (LabelModel label : labelList) {
            Rect rect = label.getRect();
            canvas.drawRect(dip2px(rect.left), dip2px(rect.top), dip2px(rect.right), dip2px(rect.bottom), labelPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = ((int) event.getX());
        int y = ((int) event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initCurLabel(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (curLabel == null) return true;
                curLabel.setX(x);
                curLabel.setY(y);
                Rect rect = new Rect(px2dip(curLabel.getX() - 20), px2dip(curLabel.getY() - 20), px2dip(curLabel.getX() + 20), px2dip(curLabel.getY() + 20));
                curLabel.setRect(rect);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d("onTouchEvent", JsonUtil.moderToString(labelList));
                break;
        }
        return true;
    }


    private void initCurLabel(int x, int y) {
        for (LabelModel label : labelList) {
            if (label.getRect().contains(px2dip(x),px2dip(y))) {
                curLabel = label;
                return;
            }
        }

        curLabel = null;
    }


    public void newLabel(String audioPath, String audioTime) {
        Rect rect = new Rect(mViewWidth / 2 - 20, mViewHeight / 2 - 20, mViewWidth / 2 + 20, mViewHeight / 2 + 20);
        labelList.add(new LabelModel(mViewWidth / 2, mViewHeight / 2, rect, audioPath, audioTime));
        invalidate();
    }

}
