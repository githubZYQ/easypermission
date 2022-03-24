package com.zyq.easypermission.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

/**
 * 动态创建shape获得drawable
 *
 * @author by Zhang YanQiang
 * @date 2022/3/24.
 */
public class EasyShapeUtil {

    /**
     * 创建一个Shape - GradientDrawable
     *
     * @param _strokeWidth - 沿边线厚度(px)；无需传入-1
     * @param _roundRadius - 圆角半径(px)；无需传入-1
     * @param _shape       - shape绘制类型(GradientDrawable.RECTANGLE、oval等)；无需传入-1，将采用默认的GradientDrawable.RECTANGLE
     * @param _strokeColor - 沿边线颜色；无需传入null/""
     * @param _fillColor   - 内部填充颜色
     * @return
     */
    public static GradientDrawable createShape(int _strokeWidth,
                                               int _roundRadius, int _shape,
                                               String _strokeColor, String _fillColor) {
        GradientDrawable gd = null;
        try {
            int strokeWidth = _strokeWidth; // px not dp
            int roundRadius = _roundRadius; // px not dp
            int strokeColor = -1;
            if (null != _strokeColor && !_strokeColor.equals("")) {
                strokeColor = Color.parseColor(_strokeColor);
            }
            int fillColor = Color.parseColor(_fillColor);

            gd = new GradientDrawable();
            gd.setColor(fillColor);

            if (-1 == _shape) {
                gd.setShape(GradientDrawable.RECTANGLE);
            } else {
                gd.setShape(_shape);
            }

            if (-1 != roundRadius) {
                gd.setCornerRadius(roundRadius);
            }
            if (-1 != strokeWidth && -1 != strokeColor) {
                gd.setStroke(strokeWidth, strokeColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gd;
    }

}
