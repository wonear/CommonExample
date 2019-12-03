package com.wonear.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.wonear.common.utils.ContextManager;

/**
 * @author wonear
 * @date : 2019/9/26 9:14
 */
public abstract class BaseCustomView extends View {


    protected int mViewWidth;
    protected int mViewHeight;


    public BaseCustomView(Context context) {
        super(context);
        initView(context, null);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    protected abstract void initView(Context context, @Nullable AttributeSet attrs);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);

        if (mViewHeight == 0) mViewHeight = dip2px(100);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }


    /**
     * MeasureSpec.UNSPECIFIED -> 未指定尺寸
     * <p>
     * 2、MeasureSpec.EXACTLA -> 精确尺寸，控件的宽高指定大小或者为FILL_PARENT
     * <p>
     * 3、MeasureSpec.AT_MOST -> 最大尺寸，控件的宽高为WRAP_CONTENT，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸
     *
     * @param measureSpec
     * @return
     */

    protected int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = Math.max(result, specSize);
        }
        return result;
    }

    protected int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = Math.max(result, specSize);
                break;
        }
        return result;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 数字
     */
    protected int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 数字
     */
    protected int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp转换为px
     *
     * @param spValue 数字
     * @return 数字
     */
    protected int sp2px(float spValue) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }


    public Rect getTextRect(String text, Paint paint) {
        Rect rect = new Rect(); // 文字所在区域的矩形
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    public int getTextWidth(String text, Paint paint) {
        Rect rect = new Rect(); // 文字所在区域的矩形
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();//宽度
    }

    public int getTextHight(String text, Paint paint) {
        Rect rect = new Rect(); // 文字所在区域的矩形
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();//文字高度
    }


    /**
     * 重新设置bitmap的大小
     *
     * @param bitmap
     * @param dst_w
     * @param dst_h
     * @return
     */
    protected Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix,
                true);
        return dstbmp;
    }


    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public int getScreenWith(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度，这边拿到的会烧掉状态栏的高度
     *
     * @return 屏幕高度
     */
    public int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


//    private void startAnim() {
//
//        ValueAnimator animator = ValueAnimator.ofFloat(0, progressCount);
//        animator.setDuration(500);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float curValue = (float) animation.getAnimatedValue();
//                animCount = curValue;
//                invalidate();
//            }
//        });
//        animator.start();
//
//    }
}
