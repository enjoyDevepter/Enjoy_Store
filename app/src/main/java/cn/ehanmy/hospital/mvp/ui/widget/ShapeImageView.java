package cn.ehanmy.hospital.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.ehanmy.hospital.R;

public class ShapeImageView extends android.support.v7.widget.AppCompatImageView {

    private Path path = new Path();
    private ShapeType shapeType = ShapeType.CIRCLE;
    private int round;
    private int width, height;

    public ShapeImageView(Context context) {
        super(context);
    }

    public ShapeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public ShapeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
        initPath();
        invalidate();
    }

    private void initPath() {
        path.reset();
        switch (shapeType) {
            case CIRCLE:
                path.addCircle(width / 2, height / 2, width < height ? width : height / 2, Path.Direction.CCW);
                break;
            case ROUND:
                path.addRoundRect(new RectF(0, 0, width, height), round, round, Path.Direction.CCW);
                break;
        }
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        initPath();
        invalidate();
    }

    private void initAttr(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShapeImageView);
        int anInt = typedArray.getInt(R.styleable.ShapeImageView_shape_type, 0);
        switch (anInt) {
            case 0:
                shapeType = ShapeType.CIRCLE;
                break;
            case 1:
                shapeType = ShapeType.ROUND;
                break;
            default:
                throw new RuntimeException("没定义的ShapeType");
        }
        round = typedArray.getDimensionPixelSize(R.styleable.ShapeImageView_radius, 0);
        typedArray.recycle();
        initPath();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;
        initPath();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.clipPath(path);
        super.draw(canvas);
    }

    public enum ShapeType {
        CIRCLE,  // 圆形
        ROUND,  // 圆角矩形
    }
}
