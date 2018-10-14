package cn.bashiquan.cmj.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mo on 2018/10/14.
 */

public class RollTextView extends TextView {
    public RollTextView (Context context) {
        super(context);
    }
    public RollTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RollTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
