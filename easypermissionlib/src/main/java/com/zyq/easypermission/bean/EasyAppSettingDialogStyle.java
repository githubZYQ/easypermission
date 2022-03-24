package com.zyq.easypermission.bean;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.zyq.easypermission.R;

/**
 * App sets popover styles
 * app设置弹窗样式
 * @author by Zhang YanQiang
 * @date 2022/3/23.
 */
public class EasyAppSettingDialogStyle {
    /**
     * 弹窗样式选择，默认 STYLE_DEFAULT
     * STYLE_DEFAULT - 蓝色弹窗，黑色文本内容
     * STYLE_SYSTEM - AlertDialog默认系统样式
     * STYLE_CUSTOM - 基于STYLE_DEFAULT自定义部分属性
     *
     * Popover style selection, STYLE_DEFAULT by default
     */
    public enum DialogStyle{
        STYLE_DEFAULT, STYLE_SYSTEM, STYLE_CUSTOM
    }

    /**
     * style=STYLE_CUSTOM时其它属性才会生效，STYLE_DEFAULT和STYLE_SYSTEM下有默认样式
     *
     * Other attributes take effect only when style=STYLE_CUSTOM. STYLE_DEFAULT and STYLE_SYSTEM have default styles
     * @param style {@link DialogStyle}
     */
    public EasyAppSettingDialogStyle(DialogStyle style) {
        this.style = style;
    }

    /**
     * 样式，默认STYLE_DEFAULT
     * Style, STYLE_DEFAULT by default
     */
    private DialogStyle style = DialogStyle.STYLE_DEFAULT;

    /**
     * 标题位置，默认居中 Gravity.CENTER
     */
    private int titleGravity = Gravity.CENTER;
    /**
     * 标题文字大小（sp），默认15sp
     */
    private int titleSize = 15;
    /**
     * 内容文字大小（sp），默认13sp
     */
    private int messageSize = 13;
    /**
     * 按钮文字大小（sp），默认14sp
     */
    private int buttonTextSize = 14;
    /**
     * 标题文字颜色（#333333），默认#333333
     */
    private String titleColor = "#333333";
    /**
     * 内容文字颜色（#333333），默认#333333
     */
    private String messageColor = "#333333";
    /**
     * 按钮主题颜色（#0086f6），左边边框和文字，右边背景色 默认#0086f6
     */
    private String buttonThemeColor = "#0086f6";
    /**
     * 左边的取消按钮文本：默认"取消"
     */
    private String cancelText = null;
    /**
     * 右边的确认按钮文本：默认"去打开"
     */
    private String confirmText = null;


    public DialogStyle getStyle() {
        return style;
    }

    /**
     * 设置一个新的样式
     * Set a new style
     * @param style  {@link DialogStyle}
     * @return
     */
    public EasyAppSettingDialogStyle setStyle(DialogStyle style) {
        this.style = style;
        return this;
    }

    public int getTitleGravity() {
        return titleGravity;
    }

    /**
     * 设置标题位置，默认居中 Gravity.CENTER
     * Sets the horizontal alignment of the text and the vertical gravity that will be used when there is extra space in the TextView beyond what is required for the text itself.
     *      See Also:{@link android.view.Gravity}
     * @param titleGravity
     */
    public EasyAppSettingDialogStyle setTitleGravity(int titleGravity) {
        this.titleGravity = titleGravity;
        return this;
    }

    public int getTitleSize() {
        return titleSize;
    }
    /**
     * 设置标题文字大小（sp），默认15sp
     *
     * Set the title text size (sp). Default: 15sp
     */
    public EasyAppSettingDialogStyle setTitleSize(int titleSize) {
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
    public EasyAppSettingDialogStyle setMessageSize(int messageSize) {
        this.messageSize = messageSize;
        return this;
    }

    public int getButtonTextSize() {
        return buttonTextSize;
    }

    /**
     * 设置按钮文字大小（sp），默认14sp
     * Set the button text size (SP) to 14sp by default
     */
    public EasyAppSettingDialogStyle setButtonTextSize(int buttonTextSize) {
        this.buttonTextSize = buttonTextSize;
        return this;
    }

    public String getTitleColor() {
        return titleColor;
    }

    /**
     * 设置标题文字颜色（#333333），默认#333333
     * Set the title text color (#333333), default #333333
     */
    public EasyAppSettingDialogStyle setTitleColor(String titleColor) {
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
    public EasyAppSettingDialogStyle setMessageColor(String messageColor) {
        this.messageColor = messageColor;
        return this;
    }

    public String getButtonThemeColor() {
        return buttonThemeColor;
    }

    /**
     * 设置按钮主题颜色（#0086f6），左边边框和文字，右边背景色 默认#0086f6
     *
     * Set the button theme color (# 0086F6), left border and text, and the right background color defaults to #0086f6
     */
    public EasyAppSettingDialogStyle setButtonThemeColor(String buttonThemeColor) {
        this.buttonThemeColor = buttonThemeColor;
        return this;
    }

    public String getCancelText(Context context) {
        if(TextUtils.isEmpty(cancelText)){
            return context.getString(R.string.setting_alert_button_cancel);
        }
        return cancelText;
    }

    /**
     * 左边的取消按钮文本：默认"取消"
     * Cancel button text at Left:Default is "Cancel"
     * @param cancelText
     * @return
     */
    public EasyAppSettingDialogStyle setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    public String getConfirmText(Context context) {
        if(TextUtils.isEmpty(cancelText)){
            return context.getString(R.string.setting_alert_button_confirm);
        }
        return confirmText;
    }

    /**
     * 右边的确认按钮文本：默认"去打开"
     * Confirm button text at Right:Default is "Understand"
     * @param confirmText
     * @return
     */
    public EasyAppSettingDialogStyle setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        return this;
    }
}
