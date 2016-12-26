package org.freeman.peiview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by freemanliu on 2016/12/19.
 * 圆饼图View
 */

public class PieView extends View {
    /**
     * 9种颜色
     */
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    /**
     * 初始绘制角度
     */
    private float mStartAngle = 0;
    /**
     * 饼图数据
     */
    private ArrayList<PieData> mData;
    /**
     * PieView宽高
     */
    private int mWidth, mHeight;
    /**
     * 画笔对象
     */
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);//打开抗锯齿
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mData)
            return;
        float currentStartAngle = mStartAngle;
        //移动画笔位置
        canvas.translate(mWidth / 2, mHeight / 2);
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rect = new RectF(-r, -r, r, r);//绘制正方形
        for (int i = 0; i < mData.size(); i++) {
            PieData pei = mData.get(i);
            mPaint.setColor(pei.getColor());
            canvas.drawArc(rect, currentStartAngle, pei.getAngle(), true, mPaint);
            currentStartAngle += pei.getAngle();
        }
    }

    public void setData(ArrayList<PieData> data) {
        this.mData = data;
        initDate(data);
        invalidate();
    }

    private void initDate(ArrayList<PieData> data) {
        //校验数据
        if(data ==null || data.size()== 0)
            return;
        float sumValue = 0;
        for(int i = 0; i < data.size(); i++){
            sumValue = data.get(i).getValue() + sumValue;
        }
        //获取总数
        for(int i = 0 ; i < data.size();i++){
            PieData peiData = data.get(i);
//        设置颜色
            peiData.setColor(mColors[i% mColors.length]);
//          计算百分比
            float percentage = peiData.getValue()/sumValue;
//          记录百分比
            peiData.setPercentage(percentage);
//                计算角度
            float angle  = 360*percentage;
//                        记录角度
            peiData.setAngle(angle);
        }
    }
}























