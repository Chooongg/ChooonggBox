package com.chooongg.core.widget.htext;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.chooongg.core.widget.htext.base.AnimationListener;
import com.chooongg.core.widget.htext.base.HTextView;

public class FallTextView extends HTextView {

    private final FallText fallText;

    public FallTextView(Context context) {
        this(context, null);
    }

    public FallTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FallTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fallText = new FallText();
        fallText.init(this, attrs, defStyleAttr);
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public void setAnimationListener(AnimationListener listener) {
        fallText.setAnimationListener(listener);
    }

    @Override
    public void setProgress(float progress) {
        fallText.setProgress(progress);
    }

    @Override
    public void animateText(CharSequence text) {
        fallText.animateText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        fallText.onDraw(canvas);
    }
}