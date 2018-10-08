package cn.ehanmy.hospital.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.textservice.TextInfo;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.ArrayList;
import java.util.List;

public class MyQTCodeFindView extends ViewfinderView {

    /**
     * 重绘时间间隔
     */
    public static final long CUSTOME_ANIMATION_DELAY = 16;

    /* ******************************************    边角线相关属性    ************************************************/

    /**
     * "边角线长度/扫描边框长度"的占比 (比例越大，线越长)
     */
    public float mLineRate = 0.1F;

    /**
     * 边角线厚度 (建议使用dp)
     */
    public float mLineDepth =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

    /**
     * 边角线颜色
     */
    public int mLineColor = Color.WHITE;

    /* *******************************************    扫描线相关属性    ************************************************/

    /**
     * 扫描线起始位置
     */
    public int mScanLinePosition = 0;

    /**
     * 扫描线厚度
     */
    public float mScanLineDepth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

    /**
     * 扫描线每次重绘的移动距离
     */
    public float mScanLineDy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

    /**
     * 线性梯度
     */
    public LinearGradient mLinearGradient;

    /**
     * 线性梯度位置
     */
    public float[] mPositions = new float[]{0f, 0.5f, 1f};

    /**
     * 线性梯度各个位置对应的颜色值
     */
    public int[] mScanLineColor = new int[]{0x00FFFFFF, Color.WHITE, 0x00FFFFFF};


    public MyQTCodeFindView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private TextPaint myPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        myPaint.setColor(Color.WHITE);
        myPaint.setStrokeWidth(1);
        myPaint.setStyle(Paint.Style.STROKE);

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;
        canvas.drawRect(frame, myPaint);


        int width = canvas.getWidth();
        int height = canvas.getHeight();

        //绘制4个角
        paint.setColor(mLineColor); // 定义画笔的颜色


        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            // 绘制扫描线
            mScanLinePosition += mScanLineDy;
            if (mScanLinePosition > frame.height()) {
                mScanLinePosition = 0;
            }
            mLinearGradient = new LinearGradient(frame.left, frame.top + mScanLinePosition, frame.right, frame.top + mScanLinePosition, mScanLineColor, mPositions, Shader.TileMode.CLAMP);
            paint.setShader(mLinearGradient);
            canvas.drawRect(frame.left, frame.top + mScanLinePosition, frame.right, frame.top + mScanLinePosition + mScanLineDepth, paint);
            paint.setShader(null);

            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }
        }

        // Request another update at the animation interval, but only repaint the laser line,
        // not the entire viewfinder mask.
        postInvalidateDelayed(CUSTOME_ANIMATION_DELAY,
                frame.left,
                frame.top,
                frame.right,
                frame.bottom);

        myPaint.setStyle(Paint.Style.FILL);

        // 左上
        canvas.drawRect(frame.left - mLineDepth, frame.top - mLineDepth, frame.left + frame.width() * mLineRate - mLineDepth, frame.top + mLineDepth - mLineDepth, myPaint);
        canvas.drawRect(frame.left - mLineDepth, frame.top - mLineDepth, frame.left + mLineDepth - mLineDepth, frame.top + frame.height() * mLineRate - mLineDepth, myPaint);

        // 右上
        canvas.drawRect(frame.right - frame.width() * mLineRate + mLineDepth, frame.top - mLineDepth, frame.right + mLineDepth, frame.top + mLineDepth - mLineDepth, myPaint);
        canvas.drawRect(frame.right - mLineDepth + mLineDepth, frame.top - mLineDepth, frame.right + mLineDepth, frame.top + frame.height() * mLineRate - mLineDepth, myPaint);

        // 左下
        canvas.drawRect(frame.left - mLineDepth, frame.bottom - mLineDepth + mLineDepth, frame.left + frame.width() * mLineRate - mLineDepth, frame.bottom + mLineDepth, myPaint);
        canvas.drawRect(frame.left - mLineDepth, frame.bottom - frame.height() * mLineRate + mLineDepth, frame.left + mLineDepth - mLineDepth, frame.bottom + mLineDepth, myPaint);

        // 右下
        canvas.drawRect(frame.right - frame.width() * mLineRate + mLineDepth, frame.bottom - mLineDepth + mLineDepth, frame.right + mLineDepth, frame.bottom + mLineDepth, myPaint);
        canvas.drawRect(frame.right - mLineDepth + mLineDepth, frame.bottom - frame.height() * mLineRate + mLineDepth, frame.right + mLineDepth, frame.bottom + mLineDepth, myPaint);
//
//        myPaint.setTextSize(40);
//        getLocalVisibleRect(globalVisibleRect);
//        myPaint.getTextBounds(info,0,info.length(),infoRect);
//        Gravity.apply(Gravity.CENTER_HORIZONTAL,infoRect.width(),infoRect.height(),globalVisibleRect,infoRect);
//        infoRect.offset(0,frame.bottom + 30);
////        infoRect.offset(frame.width()/2-infoRect.width()/2,frame.bottom + 20);
//        canvas.drawText(info,infoRect.bottom,infoRect.left,myPaint);
//    }
//    private Rect globalVisibleRect = new Rect();
//    private Rect infoRect = new Rect();
//    private String info = "请将二维码放入框内";
    }
}

