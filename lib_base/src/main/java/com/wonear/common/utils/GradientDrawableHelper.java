package com.wonear.common.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import android.view.View;

/**
 * @author wonear
 * @description :
 * @date : 2019/9/19 17:37
 */

public class GradientDrawableHelper {
    private GradientDrawable gradientDrawable;
    private View view;

    private GradientDrawableHelper(View view) {
        this.view = view;
        this.gradientDrawable = (GradientDrawable) view.getBackground().mutate();
    }

    public static GradientDrawableHelper whit(View view) {
        return new GradientDrawableHelper(view);
    }

    public GradientDrawableHelper setColor(int colorId) {
        this.gradientDrawable.setColor(ContextCompat.getColor(this.view.getContext(), colorId));
        return this;
    }

    public GradientDrawableHelper setColor(String color) {
        this.gradientDrawable.setColor(Color.parseColor(color));
        return this;
    }

    public GradientDrawableHelper setCornerRadius(float radius) {
        this.gradientDrawable.setCornerRadius(radius);
        return this;
    }

    public GradientDrawableHelper setCornerRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        float[] radius = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        this.gradientDrawable.setCornerRadii(radius);
        return this;
    }

    public GradientDrawableHelper setStroke(int strokeWidth, int color) {
        this.gradientDrawable.setStroke(strokeWidth, ContextCompat.getColor(this.view.getContext(), color));
        return this;
    }

    public GradientDrawableHelper setStroke(int strokeWidth, String color) {
        this.gradientDrawable.setStroke(strokeWidth, Color.parseColor(color));
        return this;
    }

    public GradientDrawableHelper setGradientColor(GradientDrawable.Orientation orientation, int[] color) {
        this.gradientDrawable.setOrientation(orientation);
        this.gradientDrawable.setColors(color);
        return this;
    }
}
