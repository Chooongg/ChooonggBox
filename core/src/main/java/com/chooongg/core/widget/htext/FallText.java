package com.chooongg.core.widget.htext;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.chooongg.core.widget.htext.base.CharacterDiffResult;
import com.chooongg.core.widget.htext.base.CharacterUtils;
import com.chooongg.core.widget.htext.base.DefaultAnimatorListener;
import com.chooongg.core.widget.htext.base.HText;
import com.chooongg.core.widget.htext.base.HTextView;

import java.util.ArrayList;
import java.util.List;

public class FallText extends HText {

    float mostCount = 20;
    float charTime = 400;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    private final List<CharacterDiffResult> differentList = new ArrayList<>();
    private long duration;
    private ValueAnimator animator;
    private int mTextHeight;

    @Override
    public void init(HTextView hTextView, AttributeSet attrs, int defStyle) {
        super.init(hTextView, attrs, defStyle);
        animator = new ValueAnimator();
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new DefaultAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd(mHTextView);
                }
            }
        });
        animator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            mHTextView.invalidate();
        });
        int n = mText.length();
        n = n <= 0 ? 1 : n;
        duration = (long) (charTime + charTime / mostCount * (n - 1));
    }


    @Override
    public void animateText(final CharSequence text) {
        mHTextView.post(() -> {
            if (mHTextView == null || mHTextView.getLayout() == null) {
                return;
            }
            oldStartX = mHTextView.getLayout().getLineLeft(0);
            FallText.super.animateText(text);
        });
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void animatePrepare(CharSequence text) {
        differentList.clear();
        differentList.addAll(CharacterUtils.diff(mOldText, mText));

        Rect bounds = new Rect();
        mPaint.getTextBounds(mText.toString(), 0, mText.length(), bounds);
        mTextHeight = bounds.height();

    }

    @Override
    protected void animateStart(CharSequence text) {
        int n = mText.length();
        n = n <= 0 ? 1 : n;
        duration = (long) (charTime + charTime / mostCount * (n - 1));
        animator.cancel();
        animator.setFloatValues(0, 1);
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    public void drawFrame(Canvas canvas) {
        float startX = mHTextView.getLayout().getLineLeft(0);
        float startY = mHTextView.getBaseline();
        float offset = startX;
        float oldOffset = oldStartX;
        int maxLength = Math.max(mText.length(), mOldText.length());

        for (int i = 0; i < maxLength; i++) {

            // draw old text
            if (i < mOldText.length()) {
                //
                float percent = progress * duration / (charTime + charTime / mostCount * (mText.length() - 1));

                mOldPaint.setTextSize(mTextSize);
                int move = CharacterUtils.needMove(i, differentList);
                if (move != -1) {
                    mOldPaint.setAlpha(255);
                    float p = percent * 2f;
                    p = p > 1 ? 1 : p;
                    float distX = CharacterUtils.getOffset(i, move, p, startX, oldStartX, gapList, oldGapList);
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, distX, startY, mOldPaint);
                } else {

                    mOldPaint.setAlpha(255);
                    float centerX = oldOffset + oldGapList.get(i) / 2;
                    float width = mOldPaint.measureText(mOldText.charAt(i) + "");

                    float p = percent * 1.4f;
                    p = p > 1 ? 1 : p;

                    p = interpolator.getInterpolation(p);
                    double angle = (1 - p) * (Math.PI);
                    if (i % 2 == 0) {
                        angle = (p * Math.PI) + Math.PI;
                    }
                    float disX = centerX + (float) (width / 2 * Math.cos(angle));
                    float disY = startY + (float) (width / 2 * Math.sin(angle));
                    mOldPaint.setStyle(Paint.Style.STROKE);
                    Path path = new Path();
                    path.moveTo(disX, disY);
                    // (m+x)/2=a ,x=2a-m
                    // (n+y)/2=b ,y=2b-n
                    path.lineTo(2 * centerX - disX, 2 * startY - disY);
                    if (percent <= 0.7) {
                        canvas.drawTextOnPath(mOldText.charAt(i) + "", path, 0, 0, mOldPaint);
                    } else {
                        float p2 = (float) ((percent - 0.7) / 0.3f);
                        mOldPaint.setAlpha((int) ((1 - p2) * 255));
                        float y = (p2) * mTextHeight;
                        Path path2 = new Path();
                        path2.moveTo(disX, disY + y);
                        path2.lineTo(2 * centerX - disX, 2 * startY - disY + y);
                        canvas.drawTextOnPath(mOldText.charAt(i) + "", path2, 0, 0, mOldPaint);
                    }

                }
                oldOffset += oldGapList.get(i);
            }

            // draw new text
            if (i < mText.length()) {

                if (!CharacterUtils.stayHere(i, differentList)) {

                    int alpha = (int) (255f / charTime * (progress * duration - charTime * i / mostCount));
                    alpha = Math.min(alpha, 255);
                    alpha = Math.max(alpha, 0);

                    float size = mTextSize / charTime * (progress * duration - charTime * i / mostCount);
                    size = Math.min(size, mTextSize);
                    size = size < 0 ? 0 : size;

                    mPaint.setAlpha(alpha);
                    mPaint.setTextSize(size);
                    float width = mPaint.measureText(mText.charAt(i) + "");
                    canvas.drawText(mText.charAt(i) + "", 0, 1, offset + (gapList.get(i) - width) / 2, startY, mPaint);
                }

                offset += gapList.get(i);
            }
        }
    }

}