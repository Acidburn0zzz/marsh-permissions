package com.guavabot.marshpermissions.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.widget.TextView;

/**
 * Retrieves from the theme and caches the primary and secondary text colors.
 * Requires a TextView and not a Context to force getting the theme of the TextView, which
 * could be overriding the theme from the Activity or the container.
 */
public class ThemeTextColors {

    int mColorPrimary = -1;
    int mColorSecondary = -1;

    public ThemeTextColors() {
    }

    /**
     * Get the primary text color from the TextView's theme.
     */
    public int getColorPrimary(TextView textView) {
        if (mColorPrimary < 0) {
            findTextColors(textView.getContext());
        }
        return mColorPrimary;
    }

    /**
     * Get the secondary text color from the TextView's theme.
     */
    public int getColorSecondary(TextView textView) {
        if (mColorSecondary < 0) {
            findTextColors(textView.getContext());
        }
        return mColorSecondary;
    }

    private void findTextColors(Context context) {
        TypedArray array = context.obtainStyledAttributes(new int[]{
                android.R.attr.textColorPrimary,
                android.R.attr.textColorSecondary
        });
        mColorPrimary = array.getColor(0, Color.BLACK);
        mColorSecondary = array.getColor(1, Color.BLACK);
        array.recycle();
    }
}