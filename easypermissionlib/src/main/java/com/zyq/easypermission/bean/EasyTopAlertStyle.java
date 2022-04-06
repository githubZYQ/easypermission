package com.zyq.easypermission.bean;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;

import com.zyq.easypermission.R;

/**
 * Set the top prompt style
 * 设置顶部提示样式
 * @author by Zhang YanQiang
 * @date 2022/4/6.
 */
public class EasyTopAlertStyle {
    /**
     * 提示样式选择，默认 STYLE_DEFAULT
     * STYLE_DEFAULT - 白色背景，黑色文本内容
     * STYLE_CUSTOM - 基于STYLE_DEFAULT自定义部分属性
     *
     * Popover style selection, STYLE_DEFAULT by default
     */
    public enum AlertStyle{
        STYLE_DEFAULT, STYLE_CUSTOM
    }

    /**
     * style=STYLE_CUSTOM时其它属性才会生效，STYLE_DEFAULT有默认样式
     *
     * Other attributes take effect only when style=STYLE_CUSTOM. STYLE_DEFAULT have default styles
     * @param style {@link AlertStyle}
     */
    public EasyTopAlertStyle(AlertStyle style) {
        this.style = style;
    }

    /**
     * 样式，默认STYLE_DEFAULT
     * Style, STYLE_DEFAULT by default
     */
    private AlertStyle style = AlertStyle.STYLE_DEFAULT;

    /**
     * 标题位置，默认居中 Gravity.LEFT
     */
    private int titleGravity = Gravity.LEFT;
    /**
     * 标题文字大小（sp），默认16sp
     */
    private int titleSize = 16;
    /**
     * 内容文字大小（sp），默认13sp
     */
    private int messageSize = 14;
    /**
     * 标题文字颜色（#333333），默认#333333
     */
    private String titleColor = "#333333";
    /**
     * 内容文字颜色（#333333），默认#333333
     */
    private String messageColor = "#333333";
    /**
     * 背景颜色（#FFFFFF），提示框背景颜色 默认#FFFFFF
     */
    private String backgroundColor = "#FFFFFF";
    /**
     * 背景圆角弧度，默认8dp
     */
    private int backgroundRadius = 8;
    /**
     * 背景z轴高度（阴影效果），不需要时设置为0，默认6dp
     */
    private int backgroundElevation = 6;
    /**
     * 背景左右两边边距，不需要时设置为0，默认10dp
     */
    private int sideMargin = 10;
    /**
     * 背景顶部边距，不需要时设置为0，默认10dp
     */
    private int topMargin = 10;

    public AlertStyle getStyle() {
        return style;
    }

    /**
     * 设置一个新的样式
     * Set a new style
     * @param style  {@link AlertStyle}
     * @return
     */
    public EasyTopAlertStyle setStyle(AlertStyle style) {
        this.style = style;
        return this;
    }


    public int getTitleGravity() {
        return titleGravity;
    }

    /**
     * 设置标题位置，默认居中 Gravity.CENTER
     * Sets the horizontal alignment of the text and the vertical gravity that will be used when there is extra space in the TextView beyond what is required for the text itself.
     *      See Also:{@link Gravity}
     * @param titleGravity
     */
    public EasyTopAlertStyle setTitleGravity(int titleGravity) {
        this.titleGravity = titleGravity;
        return this;
    }

    public int getTitleSize() {
        return titleSize;
    }
    /**
     * 设置标题文字大小（sp），默认15sp
     *
     * Set the title text size (sp). Default: 16sp
     */
    public EasyTopAlertStyle setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public int getMessageSize() {
        return messageSize;
    }
    /**
     * 设置内容文字大小（sp），默认13sp
     * Set the content text size (sp) to 13sp by default
     */
    public EasyTopAlertStyle setMessageSize(int messageSize) {
        this.messageSize = messageSize;
        return this;
    }


    public String getTitleColor() {
        return titleColor;
    }

    /**
     * 设置标题文字颜色（#333333），默认#333333
     * Set the title text color (#333333), default #333333
     */
    public EasyTopAlertStyle setTitleColor(String titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public String getMessageColor() {
        return messageColor;
    }
    /**
     * 设置内容文字颜色（#333333），默认#333333
     *
     * Set the content text color (#333333), default #333333
     */
    public EasyTopAlertStyle setMessageColor(String messageColor) {
        this.messageColor = messageColor;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * 设置背景颜色（#FFFFFF），默认#FFFFFF
     *
     * Set the background color (#FFFFFF), default #FFFFFF
     */
    public EasyTopAlertStyle setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getBackgroundRadius() {
        return backgroundRadius;
    }

    /**
     * 设置背景圆角弧度，默认8dp
     *
     * Set the background fillet radian, default 8dp
     * @param backgroundRadius
     * @return
     */
    public EasyTopAlertStyle setBackgroundRadius(int backgroundRadius) {
        this.backgroundRadius = backgroundRadius;
        return this;
    }

    public int getBackgroundElevation() {
        return backgroundElevation;
    }

    /**
     * 设置背景z轴高度（阴影效果），不需要时设置为0，默认6dp
     *
     * Set the background z-axis height (shadow effect), 0 if not needed, 6dp by default
     * @param backgroundElevation
     * @return
     */
    public EasyTopAlertStyle setBackgroundElevation(int backgroundElevation) {
        this.backgroundElevation = backgroundElevation;
        return this;
    }

    public int getSideMargin() {
        return sideMargin;
    }

    /**
     * 设置背景左右两边边距，不需要时设置为0，默认10dp
     *
     * Set the left and right margins of the background. If not needed, set it to 0. The default is 10dp
     * @param sideMargin
     * @return
     */
    public EasyTopAlertStyle setSideMargin(int sideMargin) {
        this.sideMargin = sideMargin;
        return this;
    }

    public int getTopMargin() {
        return topMargin;
    }

    /**
     * 设置背景顶部边距，不需要时设置为0，默认10dp
     *
     * Set the top margin of the background, 0 if not needed, 10dp by default
     * @param topMargin
     * @return
     */
    public EasyTopAlertStyle setTopMargin(int topMargin) {
        this.topMargin = topMargin;
        return this;
    }
}
