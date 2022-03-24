package com.zyq.easypermission.bean;

/**
 * 权限提示信息
 * @author by Zhang YanQiang
 * @date 2022/3/17.
 */
public class PermissionAlertInfo {
    /**
     * Title of permission prompt, for example, need to locate permission
     * 权限提示的标题，如：需要定位权限
     */
    public String alertTitle;
    /**
     * Permission prompt content, such as: used to confirm your current location, convenient driver to accurately arrive at your side
     * 权限提示的内容，如：用于确认您当前的位置，方便司机准确地到达您的身边
     */
    public String alertMessage;

    public PermissionAlertInfo(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public PermissionAlertInfo(String alertTitle, String alertMessage) {
        this.alertTitle = alertTitle;
        this.alertMessage = alertMessage;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public PermissionAlertInfo setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
        return this;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public PermissionAlertInfo setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
        return this;
    }
}
