package com.ervic.mac.gobvalle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by ervic on 12/13/17.
 */

public class FontAwesome extends android.support.v7.widget.AppCompatTextView {


    public FontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesome(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "/fontawesome-webfont.ttf");
        setTypeface(tf);
    }

}