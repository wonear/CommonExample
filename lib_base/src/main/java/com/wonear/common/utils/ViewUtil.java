package com.wonear.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class ViewUtil {

    /**
     * 动态设置TextView的右图标
     *
     * @param textView 控件
     * @param resId    图标id
     */
    public static void drawableRight(TextView textView, int resId) {
        if (resId == 0) {
            textView.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawable = textView.getContext().getResources().getDrawable(resId);
        //注意查看方法TextView.setCompoundDrawables(Drawable, Drawable, Drawable, Drawable)
        //的注释，要求设置的drawable必须已经通过Drawable.setBounds方法设置过边界参数
        //所以，此种方式下该行必不可少
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 动态设置TextView的左图标
     *
     * @param view       TextView
     * @param drawableId 图标id
     */
    public static void drawableLeft(TextView view, int drawableId) {
        drawableLeft(view, drawableId, 0, 0);
    }


    /**
     * 动态设置textview的左图标
     *
     * @param view       TextView
     * @param drawableId 图标id
     * @param iconWith   宽
     * @param iconHight  高
     */
    public static void drawableLeft(TextView view, int drawableId, int iconWith, int iconHight) {

        Context context = ContextManager.getContext();
        Drawable drawable = null;
        if (drawableId != 0) {
            drawable = context.getResources().getDrawable(drawableId);
            if (iconWith == 0) {
                iconWith = drawable.getMinimumWidth();
            } else {
                iconWith = DimensionUtil.dip2px(iconWith);
            }
            if (iconHight == 0) {
                iconHight = drawable.getMinimumHeight();
            } else {
                iconHight = DimensionUtil.dip2px(iconHight);
            }

            drawable.setBounds(0, 0, iconWith, iconHight);
        }

        view.setCompoundDrawables(drawable, null, null, null);
    }


    public static Bitmap drawableToBitmap(Drawable drawable, float width, float height) {
//        if (drawable instanceof BitmapDrawable) {
//            return ((BitmapDrawable) drawable).getBitmap();
//        }
        Bitmap bitmap = Bitmap.createBitmap(((int) width), ((int) height), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
