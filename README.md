# EasyPermission
README: [中文](https://github.com/githubZYQ/easypermission/blob/master/README.md) | [English](https://github.com/githubZYQ/easypermission/blob/master/README-en.md)
# EasyPermission简介
这个是一个方便Android中权限管理的库，它使得申请权限和业务代码逻辑简单分离，不去关心权限的申请和回调。
## 初衷
以前你是怎么管理Android的权限的？<br> 
先判断有没有权限，再申请权限，最后onRequestPermissionsResult中检查一个个结果，再执行自己的业务逻辑？<br> 
有一个打电话的权限，好多地方都要用，你每次使用打电话都要写一遍权限的判断申请和结果处理？<br> 
那现在有一个好消息：<br> 
这儿有一个方便Android中权限管理的库，你告诉它你需要的权限，然后再告诉它你想要执行什么，就可以了。
## 解释
1.module-**easypermissionlib**是EasyPermission 的核心源码code；<br> 
2.module-**app**是EasyPermission的一个使用demo；<br> 
# 集成方法
[![](https://jitpack.io/v/githubZYQ/easypermission.svg)](https://jitpack.io/#githubZYQ/easypermission)
第一步. 添加JitPack
将其添加到根build.gradle中.
````groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
````
	
第二步. 添加依赖
````groovy
dependencies {
	        implementation 'com.github.githubZYQ:easypermission:v1.0.0'
	}
````
第三步.	调用 onRequestPermissionsResult().
将要使用EasyPermission的Activity中的**onRequestPermissionsResult**,调用EasyPermissionHelper.getInstance().onRequestPermissionsResult即可；<br> 
如果你有BaseActivity，那么只需要在BaseActivity中设置一次即可。
````java
 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用EasyPermissionHelper注入回调
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
````
# 功能使用
接下来看下怎么使用。
## 1.检测权限
只需要调用EasyPermission的**hasPermission**方法,支持多个权限同时传入。
````java
EasyPermission.build().hasPermission(this, Manifest.permission.CALL_PHONE);
````
## 2.申请权限
如果你在应用启动时需要申请权限，而且并不关注权限的结果，<br> 
只需要调用EasyPermission的**requestPermission**方法，支持多个权限传入。<br> 
````java
 EasyPermission.build().requestPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
````
## 3.需要权限的结果
如果你需要知道申请权限后用户的选择结果，同时去执行自己的方法myVoid(),<br> 
那么在**onPermissionsAccess**中去做就可以了，<br> 
在**onPermissionsDismiss**是用户拒绝了权限的反馈。
````java
 EasyPermission.build()
                .mRequestCode(RC_CODE_CALLPHONE)
                .mContext(NeedReslutActivity.this)
                .mPerms(Manifest.permission.CALL_PHONE)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        //做你想做的
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        //你的权限被用户拒绝了你怎么办
                    }
                }).requestPermission();

````
## 4.有时用户拒绝了权限，而且禁止了弹出询问，我该怎么办？
同上，只要在**onDismissAsk**中，就可以得到被禁止的结果，同时你要注意**onDismissAsk**默认返回**false**，<br> 
如果你自己修改**return true**，将视为已经处理了禁止结果，将不再回调**onPermissionsDismiss**这个方法，<br> 
调用**openAppDetails**方法，可以弹窗引导用户去设置界面设置权限，在**onActivityResult**中检查结果。
````java
 EasyPermission easyPermission = EasyPermission.build()
                .mRequestCode(RC_CODE_CALLPHONE)
                .mContext(DismissAskActivity.this)
                .mPerms(Manifest.permission.CALL_PHONE)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        //做你想做的
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        //你的权限被用户拒绝了你怎么办
                    }

                    @Override
                    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                        //你的权限被用户禁止了并且不能请求了你怎么办
                        easyPermission.openAppDetails(DismissAskActivity.this, "Call Phone - Give me the permission to dial the number for you");
                        return true;
                    }
                });
        easyPermission.requestPermission();

        
        @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (EasyPermission.APP_SETTINGS_RC == requestCode) {
                    //设置界面返回
                    //Result from system setting
                    if (easyPermission.hasPermission(DismissAskActivity.this)) {
                        //做你想做的
                    } else {
                        //从设置回来还是没给你权限
                    }
        
                }
            }
````
# 最后
祝所有人平安幸福、家庭和睦、身体健康。<br> 
愿世界和平，不再被战争所累。<br> 
如果你需要使用[蓝灯](https://github.com/getlantern/lantern)去F&Q查资料，输入我的邀请码 **YPH99Z5** 来获得三个月的蓝灯专业版！[立即下载]( https://github.com/getlantern/forum)<br> 
有任何疑问，可以及时反馈给我；<br> 
如果你觉得还不错，请点赞o(￣▽￣)ｄ。