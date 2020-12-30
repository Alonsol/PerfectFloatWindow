# PerfectFloatWindow

Android悬浮窗，绝对是目前相关悬浮窗开源库最完美的适配方案。目前已经适配华为，小米，vivo，oppo，一加，三星，魅族，索尼，LG,IQOO,努比亚，中兴，金立，360，锤子等目前是市面上主流机型包括非主流机型，兼容4.4以上包括android11版本。调用方便，配置少，后续会新增更多功功能，绝对满足你的定制化需求


# 特性
 1. 支持悬浮窗内容自定义
 
 2. 内部已处理权限校验，以及设置页面跳转
 
 3. 支持builder模式，方便动态配置
 
 4. 支持悬浮窗手势滑动
 
 5. 适配vivo，oppo等第三方权限管理器跳转  
 
 6. 支持应用内以及应用外全局弹窗

 7. 权限开启弹窗支持用户自定义

### 1.初始化悬浮窗控件
``` kotlin
        //定义悬浮窗助手
        floatHelper = FloatClient.Builder()
            .with(this)
            .addView(view)
            //是否需要展示默认权限提示弹窗，建议使用自己的项目中弹窗样式（默认开启）
            .enableDefaultPermissionDialog(false)
            .setClickTarget(MainActivity::class.java)
            .addPermissionCallback(object : IFloatPermissionCallback {
                override fun onPermissionResult(granted: Boolean) {
                    //（建议使用addPermissionCallback回调中添加自己的弹窗）
                    Toast.makeText(this@MainActivity, "granted -> $granted", Toast.LENGTH_SHORT)
                        .show()
                    if (!granted) {
                        //申请权限
                        floatHelper?.requestPermission()
                    }
                }
            })
            .build()
```

### 2.开启默认弹窗,默认开启（建议开发者根据自己的样式进行）
``` kotlin
    enableDefaultPermissionDialog(true)
```
### 3.悬浮窗权限回调 用户设置该回调后，可以处理自己的回调逻辑，设置该监听后，enableDefaultPermissionDialog选项失效
``` kotlin
    addPermissionCallback(object : IFloatPermissionCallback {
                override fun onPermissionResult(granted: Boolean) {
                    //（建议使用addPermissionCallback回调中添加自己的弹窗）
                    //granted = true 权限通过 granted = false 权限拒绝
                    if (!granted) {
                        //申请权限
                        floatHelper?.requestPermission()
                    }
                }
            })
```
### 4.申请悬浮窗权限
``` kotlin
    floatHelper?.requestPermission()
```

### 5.设置点击跳转目标
``` kotlin
    floatHelper?.setClickTarget(MainActivity::class.java)
```

### 6.开启悬浮窗
``` kotlin
    floatHelper?.show()
```

### 7.关闭悬浮窗
``` kotlin
    floatHelper?.dismiss()
```

### 8.关闭悬浮窗并释放资源
``` kotlin
    override fun onDestroy() {
        super.onDestroy()
        floatHelper?.release()
    }
```

### 9.更新悬浮窗控件
``` kotlin
    private fun initCountDown() {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //更新悬浮窗内容（这里根据自己的业务进行扩展）
                tvContent.text = getLeftTime(millisUntilFinished)
            }

            override fun onFinish() {

            }
        }
        countDownTimer?.start()
    }

    fun getLeftTime(time: Long): String {
        val formatter = SimpleDateFormat("HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
        return formatter.format(time)
    }
```
### 10.权限申请
``` android
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 ```
### 11.配置
``` 
    implementation 'com.alonsol:floatserver:1.0.0'
 ```

### 结语
    PerfectFloatWindow做了大量的机型测试，满足绝大部分市场上机型，欢迎大家提供宝贵意见
