package com.yy.perfectfloatwindow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity() {
//    private var floatHelper: FloatHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

//        floatHelper = FloatHelper(this)
//            .content("")
//            .setTarget(MainActivity::class.java)
//            .startServer()

        btnShow.setOnClickListener {
//            floatHelper?.show()
        }


        btnClose.setOnClickListener {
//            floatHelper?.dismissFloat()
        }


    }
}
