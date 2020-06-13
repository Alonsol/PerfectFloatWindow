# PerfectFloatWindow
android悬浮窗，目前已经适配华为，小米，vivo，oppo，一加，三星，魅族，索尼，LG,IQOO,努比亚，中兴，金立，360，锤子等目前是市面上主流机型包括非主流机型，兼容4.4以上所有版本

# 特性
 1. 支持悬浮窗内容自定义
 
 2. 内部已处理权限校验，以及设置页面跳转
 
 3. 支持builder模式，方便动态配置
 
 4. 支持悬浮窗手势滑动
 
 5. 适配vivo，oppo等第三方权限管理器跳转  

### 1.初始化悬浮窗控件
``` kotlin
        var view = View.inflate(this, R.layout.float_view, null)
        ivIcon = view.findViewById(R.id.ivIcon)
        tvContent = view.findViewById(R.id.tvContent)

        floatHelper = FloatClient.Builder()
            .with(this)
            .addView(view) //添加悬浮窗内容
            .setClickTarget(MainActivity::class.java) //点击跳转目标
            .build()
```

### 2.设置点击跳转目标
``` kotlin
    setClickTarget(MainActivity::class.java)
```

### 3.开启悬浮窗
``` kotlin
    floatHelper?.show()
```

### 4.关闭悬浮窗
``` kotlin
    floatHelper?.dismiss()
```

### 5.关闭悬浮窗并释放资源
``` kotlin
    override fun onDestroy() {
        super.onDestroy()
        floatHelper?.release()
    }
```

### 6.更新悬浮窗控件
``` kotlin
    private fun initCountDown() {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
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
