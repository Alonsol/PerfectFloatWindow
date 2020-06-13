package com.yy.perfectfloatwindow

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yy.floatserver.FloatClient
import com.yy.floatserver.FloatHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var floatHelper: FloatHelper? = null
    private lateinit var ivIcon: ImageView
    private lateinit var tvContent: TextView
    private var countDownTimer:CountDownTimer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var view = View.inflate(this, R.layout.float_view, null)

        ivIcon = view.findViewById(R.id.ivIcon)
        tvContent = view.findViewById(R.id.tvContent)

        floatHelper = FloatClient.Builder()
            .with(this)
            .addView(view)
            .setClickTarget(MainActivity::class.java)
            .build()

        btnShow.setOnClickListener {
            floatHelper?.show()
        }


        btnClose.setOnClickListener {
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
