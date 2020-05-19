package com.example.foodpanda.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

public class MenuScrollView extends NestedScrollView {

    public Callbacks mCallbacks;

    public MenuScrollView(Context context) {
        super(context);
    }

    public MenuScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged(l, t, oldl, oldt);
        }
    }
    //定义接口用于回调
    public interface Callbacks {
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }

}
