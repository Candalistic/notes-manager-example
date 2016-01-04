package com.ten.dmitry.scrollable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextViewIndexed extends TextView {
    public int view_index;
    public TextViewIndexed(Context context, AttributeSet attrs, int index){
        super(context, attrs);
        view_index = index;
    }
}
