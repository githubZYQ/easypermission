# EasyPermission
README: [中文](https://github.com/githubZYQ/easypermission/blob/master/README.md) | [English](https://github.com/githubZYQ/easypermission/blob/master/README-en.md)
# Introduction to the EasyPermission
* This is a convenient library for permission management in Android, which makes application permission and business code logic be separated easily, 
and does not care about permission application and callback.
* The application and judgment of Android dynamic permission can be completed in one sentence: <br>
requestPermission(Manifest.permission.CAMERA)<br>
* The latest version 2.0 is a major update：<br>
1. Added support for dynamic Settings of "PermissionAlertInfo"<br>
2. Implementing an Activity monitor does not require an activity to be passed in. You can invoke permission requests anywhere
（Activity，Fragment，View，Service，BroadcastReceiver）<br>
3. Add special permission processing tools and demonstrations (notification bar, suspension window, location service)
4. OpenAppDetails () can be called directly from the EasyPermissionResult callback.
5. Added setAutoOpenAppDetails to automatically fire openAppDetails() when disabled if PermissionAlertInfo has a value.
6. Increase EasyAppSettingDialogStyle, pop-up support custom text color, size, button text and theme colors, etc.
## The original
* How did you manage Android permissions in the past?<br> 
1. Determine whether you permission first
2. Then apply for permission
3. Finally onRequestPermissionsResult check one by one as a result, to execute the business logic?<br>
* In response to the latest privacy policy, the right to request time limit hope that the function description can pop up to inform users? <br>
* There is a call authority, many places to use, you have to write every call authority judgment application and result processing?<br> 
* Now here's the good news:<br> 
* There's a library for permissions management in Android where you tell it what permissions you need and then tell it what you want to execute.
## The Explain
1. module-**easypermissionlib** is EasyPermission Core source code；<br> 
2. module-**app** is one EasyPermission demo；<br> 
# Integration method
The latest version
[![The latest version](https://jitpack.io/v/githubZYQ/easypermission.svg)](https://jitpack.io/#githubZYQ/easypermission)
## Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
````groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
````

## Step 2. Add the dependency
````groovy
dependencies {
        implementation 'com.github.githubZYQ:easypermission:v2.0.15'
	}
````
## Step 3.	Initial Configuration.
### 1.The initialization is done in onCreate of the Application
````java
 @Override
    public void onCreate() {
        super.onCreate();
        //Do this before applying for permission for the first time. It is recommended to do this in Application onCreate()
        EasyPermissionHelper.getInstance().init(this);
    }
````
### 2.In the Activity that will use EasyPermission's**onRequestPermissionsResult**And**onActivityResult**,<br>
Which Activity need,Call EasyPermissionHelper.getInstance().onRequestPermissionsResult And onActivityResult；<br>
If you have the BaseActivity，then only need to call onRequestPermissionsResult And onActivityResult in BaseActivity。<br>
（To implement automatic callback for authorization results, you do not need to configure the two methods）
````java
 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Use EasyPermissionHelper to inject callbacks
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
````
````java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Use EasyPermissionHelper to inject callbacks
        EasyPermissionHelper.getInstance().onActivityResult(requestCode, resultCode, data);
    }
````
# Function using
Now let's see how to use it.
## 1.To check the permission
All you need to do is call the **hasPermission** method of EasyPermission, which allows multiple permissions to be passed in at the same time.
````java
EasyPermission.build().hasPermission(Manifest.permission.CAMERA);
````
## 2.To apply for permission
If you need to apply for permissions when your application starts, and you don't care about the result of permissions,<br> 
You only need to call the **requestPermission** method of EasyPermission, which supports multiple permissions passing in.<br> 
````java
 EasyPermission.build().requestPermission(Manifest.permission.CAMERA);
````
## 3.Results that require permissions
If you need to know the result of the user's selection after applying permission, while executing your own method myVoid(),<br> 
So just do it in **onPermissionsAccess**,<br> 
In **onPermissionsDismiss** is the user rejecting permission feedback.
````java
 EasyPermission.build()
                .mRequestCode(RC_CODE_PERMISSION)
                .mPerms(Manifest.permission.CAMERA)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        //Do what you want
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        //What do you do if your access is denied by the user
                    }
                }).requestPermission();

````
## 4.Sometimes the user rejects the permission, and the popup request is forbidden, what should I do?
* In fact, in the new version you only need to dismissask text via **mAlertInfo**, which now handles pop-ups by default. That means you don't need to rewrite the ** onask ** and **openAppDetails** methods
* As long as you dismissask ** in **onDismissAsk**, you'll get that forbidden result, and you'll want to note that **onDismissAsk** returns false** by default
* If you modify **return true** yourself, you will have handled the forbidden result and will not call back **onPermissionsDismiss**
* Call the **openAppDetails** method to pop up and direct the user to the Settings screen. OnPermissionsAccess will be automatically called back upon success
````java
easyPermission = EasyPermission.build()
        .mRequestCode(RC_CODE_PERMISSION)
        .mPerms(Manifest.permission.CAMERA)
        .setAutoOpenAppDetails(true) //被拒绝并禁止时是否自动弹窗提醒，默认是true
        .mAlertInfo( new PermissionAlertInfo("**APP need to apply for camera permission",
        "**APP need to apply for camera shooting permission, so that you can scan the TWO-DIMENSIONAL code by scanning; Change the profile picture of your account by taking a photo; Take a photo and upload some id information needed to register an account. Refusal or cancellation of authorization will affect the above functions, but will not affect the use of other services"))
        .mResult(new EasyPermissionResult() {
@Override
public void onPermissionsAccess(int requestCode) {
        super.onPermissionsAccess(requestCode);
        //Permission approved
        }

@Override
public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
        super.onPermissionsDismiss(requestCode, permissions);
        //Permission denied
        }
@Override
public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
        //Permission is denied and no further questioning is allowed
        //Here true means intercept processing, no more callback onPermissionsDismiss;
        return super.onDismissAsk(requestCode,permissions);
        }
@Override
public void openAppDetails() {
        //A dialog box is displayed asking you to set the default permission details. After the permission operation is complete on the Settings page, the system automatically calls back toonPermissionsAccess()
        super.openAppDetails();
        //If the style is not satisfactory, you can pop up your own description popover and call "goToAppSettings()" when the user confirms;then jump Settings page is complete
        }
        ).requestPermission();
````
## 5.Popover style custom
The permissions library is quite convenient to use, but the text color of the popover needs to be changed. It is not like writing the popover by yourself every time. Can you set the text size and color? No problem, we support popover custom styles.
### Use the default classic style (similar to JINGdong, Xiaohongshu)
````java
EasyPermissionHelper.getInstance().setDialogStyle(new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_DEFAULT));
````
### Use the system's own popover style
````java
EasyPermissionHelper.getInstance().setDialogStyle(new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_SYSTEM));
````
### Use system custom popover styles
````java
EasyPermissionHelper.getInstance().setDialogStyle(
        new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_CUSTOM)
        .setTitleGravity(Gravity.CENTER)//设置居中
        .setTitleSize(17)//设置标题样式
        .setTitleColor("#333333")
        .setMessageSize(14)//设置内容样式
        .setMessageColor("#666666")
        .setButtonTextSize(14)//设置按钮样式
        .setButtonThemeColor("#FF0000")
        .setCancelText("取消")//设置按钮文本
        .setConfirmText("去打开"));
````
### Fully custom popovers
You need to set the preceding mode only once after the initial call and it takes effect globally. If the above method still does not satisfy your appetite, then you have to control the popover pull yourself。<br>
Rewrite **openAppDetails** in **EasyPermissionResult**
````java
 @Override
public void openAppDetails() {
      //Display your own popup on the Go to App Settings details page to tell the user what permissions we want to open
      //When users click on the confirmation call easyPermission. GoToAppSettings (); The jump Settings page is complete
      }
````
## 6.Top prompt message style custom
权限库用起来蛮方便的，但是顶部提示的背景颜色需要改一下，能不能设置一下文字大小、颜色、背景色？没问题，咱们支持顶部提示信息自定义样式。
### Use the default classic style
````java
EasyPermissionHelper.getInstance().setTopAlertStyle(
        new EasyTopAlertStyle(EasyTopAlertStyle.AlertStyle.STYLE_DEFAULT));
````

### Use custom prompt styles
````java
EasyPermissionHelper.getInstance().setTopAlertStyle(
        new EasyTopAlertStyle(EasyTopAlertStyle.AlertStyle.STYLE_CUSTOM)
        .setTitleGravity(Gravity.LEFT)//默认居左
        .setTitleSize(16)//设置标题样式，默认16sp
        .setTitleColor("#333333")
        .setMessageSize(14)//设置内容样式，默认14sp
        .setMessageColor("#333333")
        .setBackgroundColor("#FFFFFF")//设置背景色，默认白色
        .setBackgroundRadius(8)//设置背景圆角弧度，默认8dp
        .setBackgroundElevation(6)//设置背景阴影范围，默认6dp
        .setTopMargin(10)//设置距离顶部标题栏间距，默认10dp
        .setSideMargin(10));//设置距离屏幕两边宽度，默认10dp
````
## 7.Other Matters needing attention
1. mAlertInfowill not automatically pop up the permission description window if it is not set. In order to meet the current increasingly strict privacy policy, please take every permission description seriously<br>
2. Permission applications are not recommended to be obtained in onNewIntent<br>
3. Related log tag for "EasyPermissionLog", the default output not too much information, if need to debug. Please open the EasyPermissionConfigs setDebug (true)<br>
4. Added setAutoOpenAppDetails to automatically fire openAppDetails() when disabled if PermissionAlertInfo has a value.
5. If the openAppDetails() style doesn't suit, you can override openAppDetails() to customize pop-up content, or block it directly in onDismissAsk()
6. Because EasyPermission in the init initialization time use ActivityLifecycleCallbacks began to monitor the activity of change, so if needed in the launchMode = "singleTask" onNewIntent request permissions, You need to reset the activity.
This can be done in two ways.
### The Way One：
````java
EasyPermissionHelper.getInstance().updateTopActivity(mContext);
easyPermission.requestPermission();
````
### The Way Two:
````java
easyPermission.mContext(mContext).requestPermission();
````
## 8.Other tools
### Location Service Management :LocationTool
After Android 9.0, even if you have obtained user authorization for location, you still cannot obtain location because the GPS location service is not enabled. Therefore, you need to process the location service. The LocationTool supports the following methods:<br> 
1. isLocationEnabled() Obtain whether the current location service is enabled<br> 
2. gotoAppSettings() Jump directly to the mobile - Settings - Security - Privacy - Location Services on/off page<br> 
### Notification Service Management :EasyNotificationTool
Notification service permissions in Android is also more special, it is not like other permissions to apply directly, like location services need to go to the system Settings, so also to the Settings page：<br> 
1. isNotificationEnabled() Whether the notification permission of the current APP is enabled<br> 
2. gotoAppSettings() Jump directly to mobile - Settings - Notifications and status bar - Notifications Management -APP Notification Settings page<br> 
> ### Hover window permission management :EasyFloatWindowTool
Floating window permission in Android is also special, it is not like other permissions to apply directly, like location services need to be opened in the system Settings, so you also need to go to the Settings page:<br> 
1. isFloatWindowEnabled() Gets whether the current APP has hover window permissions<br> 
2. gotoAppSettings() Jump directly to mobile - Settings - Application Management - Special application permissions - display in the upper layer of other applications -APP Settings page<br> 
# The last
* If you have better plans and ideas, welcome to leave a message or private letter<br>
* Peace, happiness, family harmony and good health to all.<br> 
* May the motherland complete its reunification at an early date and the world live in peace and prosperity.<br> 
* If you need [lantern](https://github.com/getlantern/lantern) to F&Q ,enter my invitation code **YPH99Z5** ,to get three months of blue light pro![Download lantern]( https://github.com/getlantern/forum)<br> 
* If you have any questions, please timely feedback to me.<br> 
* If you like it, please thumb up.o(￣▽￣)ｄ。