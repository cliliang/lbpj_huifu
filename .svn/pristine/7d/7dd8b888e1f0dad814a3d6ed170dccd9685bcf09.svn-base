package com.baluobo.common.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * desc: 自定义的文字图标view
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class IconTextView extends TextView {
    private static Typeface iconfont = null;

    public IconTextView(Context context) {
        super(context);
        initFont(context);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initFont(context);
    }

    private void initFont(Context context) {
        Typeface font = getFont(context);
        if (font != null) {
            setTypeface(font);
        }
    }

    private static Typeface getFont(Context context) {
        if (iconfont == null) {
            synchronized (IconTextView.class) {
                if (iconfont == null && context != null) {
                    iconfont = Typeface.createFromAsset(context.getAssets(), "icomoon/fonts/icomoon.ttf");
                }
            }
        }

        return iconfont;
    }
}
