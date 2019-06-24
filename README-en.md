# EasyPermission
README: [中文](https://github.com/githubZYQ/easypermission/blob/master/README.md) | [English](https://github.com/githubZYQ/easypermission/blob/master/README-en.md)
# Introduction to the EasyPermission
This is a convenient library for permission management in Android, which makes application permission and business code logic be separated easily, 
and does not care about permission application and callback.
## The original
How did you manage Android permissions in the past?<br> 
Determine whether you permission first, then apply for permission, finally onRequestPermissionsResult check one by one as a result, to execute the business logic?<br> 
There is a call authority, many places to use, you have to write every call authority judgment application and result processing?<br> 
Now here's the good news:<br> 
There's a library for permissions management in Android where you tell it what permissions you need and then tell it what you want to execute.
## The Explain
1.module-**easypermissionlib** is EasyPermission Core source code；<br> 
2.module-**app** is one EasyPermission demo；<br> 
# Integration method
[![](https://jitpack.io/v/githubZYQ/easypermission.svg)](https://jitpack.io/#githubZYQ/easypermission)
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
````groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
````
	
Step 2. Add the dependency
````groovy
dependencies {
	        implementation 'com.github.githubZYQ:easypermission:v1.0.0'
	}
````
Step 3.	call onRequestPermissionsResult().
Going to use EasyPermission **onRequestPermissionsResult** in Activity, call EasyPermissionHelper. GetInstance (). OnRequestPermissionsResult can;<br> 
If you have BaseActivity, you only need to set it up once in BaseActivity.
````java
 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Inject the callback using EasyPermissionHelper
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
````
# Function using
Now let's see how to use it.
## 1.To check the permission
All you need to do is call the **hasPermission** method of EasyPermission, which allows multiple permissions to be passed in at the same time.
````java
EasyPermission.build().hasPermission(this, Manifest.permission.CALL_PHONE);
````
## 2.To apply for permission
If you need to apply for permissions when your application starts, and you don't care about the result of permissions,<br> 
You only need to call the **requestPermission** method of EasyPermission, which supports multiple permissions passing in.<br> 
````java
 EasyPermission.build().requestPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
````
## 3.Results that require permissions
If you need to know the result of the user's selection after applying permission, while executing your own method myVoid(),<br> 
So just do it in **onPermissionsAccess**,<br> 
In **onPermissionsDismiss** is the user rejecting permission feedback.
````java
 EasyPermission.build()
                .mRequestCode(RC_CODE_CALLPHONE)
                .mContext(NeedReslutActivity.this)
                .mPerms(Manifest.permission.CALL_PHONE)
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
Same as above, so long as in **onDismissAsk**, you can get the banned result, while you want to notice that **onDismissAsk** returns false by default,<br> 
So if you modify return true yourself, you're going to look at it as having handled the ban result, you're not going to call back the **onPermissionsDismiss** method,<br> 
Call the **openAppDetails** method, you can popover to guide the user to set the permissions in the interface, and check the results in the **onActivityResult**.
````java
 EasyPermission easyPermission = EasyPermission.build()
                .mRequestCode(RC_CODE_CALLPHONE)
                .mContext(DismissAskActivity.this)
                .mPerms(Manifest.permission.CALL_PHONE)
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

                    @Override
                    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                        //What do you do if your permissions are blocked by the user and cannot be requested
                        easyPermission.openAppDetails(DismissAskActivity.this, "Call Phone - Give me the permission to dial the number for you");
                        return true;
                    }
                });
        easyPermission.requestPermission();

        
        @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (EasyPermission.APP_SETTINGS_RC == requestCode) {
                    //Setting interface return
                    //Result from system setting
                    if (easyPermission.hasPermission(DismissAskActivity.this)) {
                        //Do what you want
                    } else {
                        //It still doesn't give you permission to go back from Settings
                    }
        
                }
            }
````
# The last
Peace, happiness, family harmony and good health to all.<br> 
May world peace, no longer be burdened by war.<br> 
If you need [lantern](https://github.com/getlantern/lantern) to F&Q ,enter my invitation code **YPH99Z5** ,to get three months of blue light pro![Download lantern]( https://github.com/getlantern/forum)<br> 
If you have any questions, please timely feedback to me.<br> 
If you like it, please thumb up.o(￣▽￣)ｄ。