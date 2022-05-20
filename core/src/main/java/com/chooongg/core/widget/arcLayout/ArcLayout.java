package com.chooongg.core.widget.arcLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chooongg.core.R;
import com.chooongg.core.widget.arcLayout.manager.ClipPathManager;

public class ArcLayout extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;
    public static final int CROP_INSIDE = 1;

    public static final int CROP_OUTSIDE = 2;
    @ArcPosition
    private int arcPosition = POSITION_TOP;
    @CropDirection
    private int cropDirection = CROP_INSIDE;

    private int arcHeight = 0;

    public ArcLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ArcLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArcLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ArcLayout);
            arcHeight = attributes.getDimensionPixelSize(R.styleable.ArcLayout_arc_height, arcHeight);
            arcPosition = attributes.getInteger(R.styleable.ArcLayout_arc_position, arcPosition);
            cropDirection = attributes.getInteger(R.styleable.ArcLayout_arc_cropDirection, cropDirection);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                final boolean isCropInside = cropDirection == CROP_INSIDE;

                switch (arcPosition) {
                    case POSITION_BOTTOM: {
                        path.moveTo(0, 0);
                        if (isCropInside) {
                            path.lineTo(0, height);
                            path.quadTo(width / 2f, height - 2 * arcHeight, width, height);
                        } else {
                            path.lineTo(0, height - arcHeight);
                            path.quadTo(width / 2f, height + arcHeight, width, height - arcHeight);
                        }
                        path.lineTo(width, 0);
                        path.close();
                        break;
                    }
                    case POSITION_TOP:
                        if (isCropInside) {
                            path.moveTo(0, height);
                            path.lineTo(0, 0);
                            path.quadTo(width / 2f, 2 * arcHeight, width, 0);
                            path.lineTo(width, height);
                        } else {
                            path.moveTo(0, arcHeight);
                            path.quadTo(width / 2f, -arcHeight, width, arcHeight);
                            path.lineTo(width, height);
                            path.lineTo(0, height);
                        }
                        path.close();
                        break;
                    case POSITION_LEFT:
                        path.moveTo(width, 0);
                        if (isCropInside) {
                            path.lineTo(0, 0);
                            path.quadTo(arcHeight * 2, height / 2f, 0, height);
                        } else {
                            path.lineTo((float) arcHeight, 0);
                            path.quadTo(-arcHeight, height / 2f, (float) arcHeight, height);
                        }
                        path.lineTo(width, height);
                        path.close();
                        break;
                    case POSITION_RIGHT:
                        path.moveTo(0, 0);
                        if (isCropInside) {
                            path.lineTo(width, 0);
                            path.quadTo(width - arcHeight * 2, height / 2f, width, height);
                        } else {
                            path.lineTo(width - arcHeight, 0);
                            path.quadTo(width + arcHeight, height / 2f, width - arcHeight, height);
                        }
                        path.lineTo(0, height);
                        path.close();
                        break;

                }
                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }


    public int getArcPosition() {
        return arcPosition;
    }

    public void setArcPosition(@ArcPosition int arcPosition) {
        this.arcPosition = arcPosition;
        requiresShapeUpdate();
    }

    public int getCropDirection() {
        return cropDirection;
    }

    public void setCropDirection(@CropDirection int cropDirection) {
        this.cropDirection = cropDirection;
        requiresShapeUpdate();
    }

    public int getArcHeight() {
        return arcHeight;
    }

    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
        requiresShapeUpdate();
    }

    @IntDef(value = {POSITION_BOTTOM, POSITION_TOP, POSITION_LEFT, POSITION_RIGHT})
    public @interface ArcPosition {
    }

    @IntDef(value = {CROP_INSIDE, CROP_OUTSIDE})
    public @interface CropDirection {
    }
}