package com.yy.perfectfloatwindow

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yy.floatserver.FloatClient
import com.yy.floatserver.FloatHelper
import com.yy.floatserver.IFloatPermissionCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var floatHelper: FloatHelper? = null
    private lateinit var ivIcon: ImageView
    private lateinit var tvContent: TextView
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var view = View.inflate(this, R.layout.float_view, null)

        ivIcon = view.findViewById(R.id.ivIcon)
        tvContent = view.findViewById(R.id.tvContent)

        //定义悬浮窗助手
        floatHelper = FloatClient.Builder()
            .with(this)
            .addView(view)
            //是否需要展示默认权限提示弹窗，建议使用自己的项目中弹窗样式
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

        btnShow.setOnClickListener {
            //开启悬浮窗
            floatHelper?.show()
        }


        btnClose.setOnClickListener {
            //隐藏悬浮窗
            floatHelper?.dismiss()
        }

        btnJump.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        initCountDown()
    }

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


    override fun onDestroy() {
        super.onDestroy()
        floatHelper?.release()
    }
}
