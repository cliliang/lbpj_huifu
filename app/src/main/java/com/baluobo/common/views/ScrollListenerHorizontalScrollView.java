package com.baluobo.common.views;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/30.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;

public class ScrollListenerHorizontalScrollView extends HorizontalScrollView {

    public ScrollListenerHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ScrollListenerHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ScrollListenerHorizontalScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    private ScrollViewListener listener;
    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null){
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public int getViewWidth(){
        return getMeasuredWidth();
    }

    public interface ScrollViewListener{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public void setOnScrollViewListener(ScrollViewListener l){
        this.listener = l;
    }


}
