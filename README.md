# EasyPermission
README: [中文](https://github.com/githubZYQ/easypermission/blob/master/README.md) | [English](https://github.com/githubZYQ/easypermission/blob/master/README-en.md)
# EasyPermission简介
* 这个是一个方便Android中权限管理的库，它使得申请权限和业务代码逻辑简单分离，不去关心权限的申请和回调。<br>
* 将安卓动态权限的申请和判断简单到一句话就能完成：<br>
requestPermission(Manifest.permission.CAMERA)<br>
* 最新2.0版本主要更新：<br>
1. 增加了支持动态设置 PermissionAlertInfo (权限提示信息)<br>
2. 实现activity监控不在要求必须传入Activity，可在任意地方调用权限申请（Activity，Fragment，View，Service，BroadcastReceiver）<br>
3. 增加特殊权限处理工具及演示（通知栏、悬浮窗、定位服务）
4. EasyPermissionResult回调中可直接调用 openAppDetails();
5. 增加setAutoOpenAppDetails,如果PermissionAlertInfo有值，则在被禁止时自动触发openAppDetails();
6. 增加EasyAppSettingDialogStyle，说明弹窗支持自定义的文本颜色、大小、按钮文本和主题颜色等；
## 初衷
* 以前你是怎么管理Android的权限的？<br> 
1. 先判断有没有权限
2. 再申请权限
3. 最后onRequestPermissionsResult中检查一个个结果，再执行自己的业务逻辑？<br> 
* 有一个打电话的权限，好多地方都要用，你每次使用打电话都要写一遍权限的判断申请和结果处理？<br>
* 应对最新隐私政策，请求权限时希望可以弹出功能说明对用户进行告知？<br>
* 那现在有一个好消息：<br> 
这儿有一个方便Android中权限管理的库，你告诉它你需要的权限，然后再告诉它你想要执行什么，就可以了。
## 解释
1. module-**easypermissionlib**是EasyPermission 的核心源码code；<br> 
2. module-**app**是EasyPermission的一个使用demo；<br> 
# 集成方法
最新版本
[![最新版本](https://jitpack.io/v/githubZYQ/easypermission.svg)](https://jitpack.io/#githubZYQ/easypermission)
## 第一步. 添加JitPack
将其添加到根build.gradle中.
````groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
````

## 第二步. 添加依赖
````groovy
dependencies {
	        implementation 'com.github.githubZYQ:easypermission:v2.0.13'
	}
````
## 第三步. 初始化配置
### 1.在Application的onCreate中完成初始化
````java
 @Override
    public void onCreate() {
        super.onCreate();
        //首次使用权限申请之前完成初始化，建议放在Application onCreate()中完成
        EasyPermissionHelper.getInstance().init(this);
    }
````
### 2.将要使用EasyPermission的Activity中的**onRequestPermissionsResult**和**onActivityResult**,<br>
* 在对应的Activity调用EasyPermissionHelper.getInstance().onRequestPermissionsResult和onActivityResult即可；<br> 
* 如果你有BaseActivity，那么只需要在BaseActivity中设置一次即可。
* 这两个方方法为了实现授权结果的自动回调，如果不需要回调可以不配置
````java
 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用EasyPermissionHelper注入回调
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
````
````java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //使用EasyPermissionHelper注入回调
        EasyPermissionHelper.getInstance().onActivityResult(requestCode, resultCode, data);
    }
````
# 功能使用
接下来看下怎么使用。
## 1.检测权限
只需要调用EasyPermission的**hasPermission**方法,支持多个权限同时传入。
````java
EasyPermission.build().hasPermission(Manifest.permission.CAMERA);
````
## 2.申请权限
如果你在应用启动时需要申请权限，而且并不关注权限的结果，<br> 
只需要调用EasyPermission的**requestPermission**方法，支持多个权限传入。<br> 
````java
 EasyPermission.build().requestPermission(Manifest.permission.CAMERA);
````
## 3.需要权限的结果
* 如果你需要知道申请权限后用户的选择结果，同时去执行自己的方法myVoid(),<br> 
* 那么在**onPermissionsAccess**中去做就可以了，<br> 
* 在**onPermissionsDismiss**是用户拒绝了权限的反馈。
````java
 EasyPermission.build()
                .mRequestCode(RC_CODE_PERMISSION)
                .mPerms(Manifest.permission.CAMERA)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        //权限已通过
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        //权限被拒绝
                    }
                }).requestPermission();

````
## 4.有时用户拒绝了权限，而且禁止了弹出询问，我该怎么办？想要在申请权限时弹窗告知用户权限的必要性怎么办？
* 事实上，在新版本只需要通过**mAlertInfo**设置了提示文本，现在已经默认处理了弹窗的展示，也就是说不需要去重写**onDismissAsk**和**openAppDetails**方法了
* 如果想要自己处理弹窗逻辑，可以通过**setAutoOpenAppDetails=false**关闭自动处理的逻辑 
* 只要在**onDismissAsk**中，就可以得到被禁止的结果，同时你要注意**onDismissAsk**默认返回**false**
* 如果你自己修改**return true**，将视为已经处理了禁止结果，将不再回调**onPermissionsDismiss**这个方法
* 调用**openAppDetails**方法，可以弹窗引导用户去设置界面设置权限，成功后会自动回调onPermissionsAccess
````java
easyPermission = EasyPermission.build()
        .mRequestCode(RC_CODE_PERMISSION)
        .mPerms(Manifest.permission.CAMERA)
        .setAutoOpenAppDetails(true)
        .mAlertInfo( new PermissionAlertInfo("**需要申请摄像头权限",
        "**需要申请摄像头拍摄权限，以便您能够通过扫一扫实现扫描二维码；通过拍照更换您帐号的头像；拍照上传一些注册帐号需要的证件信息。拒绝或取消授权将影响以上功能，不影响使用其他服务"))
        .mResult(new EasyPermissionResult() {
            @Override
            public void onPermissionsAccess(int requestCode) {
                super.onPermissionsAccess(requestCode);
                //权限已通过
            }

            @Override
            public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                super.onPermissionsDismiss(requestCode, permissions);
                //权限被拒绝
            }

            @Override
            public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                //权限被拒绝并禁止再次询问
                return super.onDismissAsk(requestCode,permissions);//这里true表示拦截处理，不再回调onPermissionsDismiss；
            }
            @Override
            public void openAppDetails() {
                //弹出默认的权限详情设置提示弹出框，在设置页完成允许操作后，会自动回调到onPermissionsAccess()
                super.openAppDetails();
                //如果样式不满意，可以弹出自定义明弹窗，在用户确认时调用 goToAppSettings();完成跳转设置页
            }).requestPermission();
````
## 5.弹窗样式自定义
权限库用起来蛮方便的，但是弹窗的文字颜色需要改一下，又不像大动干戈地每次自己去写弹窗，能不能设置一下文字大小、颜色？没问题，咱们支持弹窗自定义样式。
### 使用默认经典样式(类似京东、小红书)
````java
EasyPermissionHelper.getInstance().setDialogStyle(new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_DEFAULT));
````
### 使用系统自带弹窗样式
````java
EasyPermissionHelper.getInstance().setDialogStyle(new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_SYSTEM));
````
### 使用自定义弹窗样式
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
### 完全自定义弹窗
以上方式只需要在初始话后设置一次，全局生效。如果以上方式依然满足不了你胃口，那只能自己去控制弹窗拉。<br>
在**EasyPermissionResult**中重写**openAppDetails**
````java
 @Override
public void openAppDetails() {
      //在前往应用设置详情页展示自己的弹窗告知用户我们需要哪些权限打开
      //在用户点击确认时调用easyPermission.goToAppSettings();完成跳转设置页
      }
````
## 6.顶部提示信息样式自定义
权限库用起来蛮方便的，但是顶部提示的背景颜色需要改一下，能不能设置一下文字大小、颜色、背景色？没问题，咱们支持顶部提示信息自定义样式。
### 使用默认经典样式
````java
EasyPermissionHelper.getInstance().setTopAlertStyle(
        new EasyTopAlertStyle(EasyTopAlertStyle.AlertStyle.STYLE_DEFAULT));
````

### 使用自定义提示样式
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
## 7.其它注意事项
1. mAlertInfo不设置将不会自动弹出权限说明弹窗,为了满足当前的日益严格的隐私政策，请对认真对待每一个权限说明<br>
2. 权限的申请不建议在onNewIntent中获取<br>
3. 相关日志tag为"EasyPermissionLog",默认不输出太多信息，如果需要调试请打开EasyPermissionConfigs.setDebug(true)<br>
4. 增加setAutoOpenAppDetails,如果PermissionAlertInfo有值，则在被禁止时自动触发 openAppDetails()
5. 如果openAppDetails()样式不满足，可以重写openAppDetails()自定义弹出内容，也可以直接在onDismissAsk()拦截
6. 由于EasyPermission在init初始化时使用ActivityLifecycleCallbacks开始监听activity变化，所以在launchMode="singleTask" onNewIntent中如果需要请求权限，需要重新设置activity。
可以使用两种方式完成。
### 方式一：
````java
EasyPermissionHelper.getInstance().updateTopActivity(mContext);
easyPermission.requestPermission();
````
### 方式二:
````java
easyPermission.mContext(mContext).requestPermission();
````
## 8.其它工具
### 定位服务管理 EasyLocationTool
Android 9.0以后即使已经获得了用户授权定位权限，由于GPS定位服务未打开，依然获取不到定位，所以还需要对定位服务进行处理，LocationTool支持以下方法：<br> 
1. isLocationEnabled() 获取当前定位服务是否开启<br> 
2. gotoAppSettings() 直接跳转到手机-设置-安全和隐私-定位服务开启/关闭的页面<br> 
### 通知服务管理 EasyNotificationTool
通知服务的权限在Android中也比较特殊，它不像其它权限那样去直接申请，像定位服务一样需要去系统设置中开启，所以也要去设置页：<br> 
1. isNotificationEnabled() 获取当前APP的通知权限是否开启<br> 
2. gotoAppSettings() 直接跳转到手机-设置-通知和状态栏-通知管理-APP通知设置页<br> 
### 悬浮窗权限管理 EasyFloatWindowTool
悬浮窗权限在Android中也比较特殊，它不像其它权限那样去直接申请，像定位服务一样需要去系统设置中开启，所以也要去设置页：<br> 
1. isFloatWindowEnabled() 获取当前APP的是否有悬浮窗权限<br> 
2. gotoAppSettings() 直接跳转到手机-设置-应用管理-特殊应用权限-显示在其他应用的上层-APP设置页<br> 
# 结束语
* 如果又更好的方案和思路，欢迎留言或者私信<br>
* 祝所有人平安幸福、家庭和睦、身体健康。<br> 
* 愿祖国早日完成统一大业，世界和平共处，繁荣发展。<br> 
* 有任何疑问，可以及时反馈给我；<br> 
* 如果你觉得还不错，请点赞o(￣▽￣)ｄ。