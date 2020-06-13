package com.yy.floatserver.server

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.FrameLayout
import com.yy.floatserver.R
import com.yy.floatserver.utils.JumpUtils
import com.yy.floatserver.utils.RomUtil
import kotlin.math.abs


/**
 * Created by yuyang
 * @description: 悬浮窗服务
 */
class FloatingServer : Service() {

    private lateinit var wmParams: WindowManager.LayoutParams
    private lateinit var mWindowManager: WindowManager
    private lateinit var mWindowView: View
    private lateinit var mContainer: FrameLayout
    private var mParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
    private var mStartX: Int = 0
    private var mStartY: Int = 0
    private var mEndX: Int = 0
    private var mEndY: Int = 0
    var mFloatBinder: FloatBinder? = null

    private var hasAdded = false
    private var startTime = 0L

    var clazz: Class<*>? = null

    override fun onCreate() {
        super.onCreate()
        initWindowParams()
        initView()
        initClick()
    }

    private fun initWindowParams() {
        mWindowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wmParams = WindowManager.LayoutParams()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            req()
        } else if (RomUtil.isMiui()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                req()
            } else {
                wmParams.type = WindowManager.LayoutParams.TYPE_PHONE
            }
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST
        }
        wmParams.format = PixelFormat.RGBA_8888
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        wmParams.gravity = Gravity.START or Gravity.TOP
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    private fun req() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
    }

    private fun initView() {
        mWindowView = LayoutInflater.from(application).inflate(R.layout.float_window_layout, null)
        mContainer = mWindowView.findViewById<View>(R.id.llContainer) as FrameLayout
        mFloatBinder = FloatBinder()
    }

    private fun addWindowView2Window(view: View) {
        if (!hasAdded) {
            try {
                view.layoutParams = mParams
                mContainer.addView(view)
                mWindowManager.addView(mWindowView, wmParams)
                val width = mWindowManager.defaultDisplay.width
                val height = mWindowManager.defaultDisplay.height
                wmParams.x = width
                wmParams.y = height / 2
                hasAdded = true
                mWindowManager.updateViewLayout(mWindowView, wmParams)
            } catch (e: Exception) {
                try {
                    mWindowManager.removeView(mWindowView)
                } catch (e: Exception) {
                }
                hasAdded = false
            }


        }
    }


    private fun removeWindowView() {
        if (hasAdded) {
            //移除悬浮窗口
            mWindowManager.removeView(mWindowView)
            hasAdded = false
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return mFloatBinder
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initClick() {
        mWindowView.setOnTouchListener(View.OnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTime = System.currentTimeMillis()
                    mStartX = event.rawX.toInt()
                    mStartY = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    mEndX = event.rawX.toInt()
                    mEndY = event.rawY.toInt()
                    if (needIntercept()) {
                        wmParams.x = event.rawX.toInt() - mWindowView.measuredWidth / 2
                        wmParams.y = event.rawY.toInt() - mWindowView.measuredHeight / 2
                        mWindowManager.updateViewLayout(mWindowView, wmParams)
                        return@OnTouchListener true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (needIntercept()) {
                        var isJump = System.currentTimeMillis() - startTime < 200
                        if (isJump) {
                            jump()
                        }
                        return@OnTouchListener true
                    } else {
                        var isJump = System.currentTimeMillis() - startTime < 200
                        if (isJump) {
                            jump()
                            return@OnTouchListener false
                        }
                    }
                }
                else -> {
                    return@OnTouchListener false
                }
            }
            false
        })

    }


    private fun jump() {

        JumpUtils.jump(this, clazz)
    }


    /**
     * 是否拦截
     * @return true:拦截;false:不拦截.
     */
    private fun needIntercept(): Boolean {
        return abs(mStartX - mEndX) > 30 || abs(mStartY - mEndY) > 30
    }


    inner class FloatBinder : Binder() {

        fun hide() {
            removeWindowView()
        }

        fun show(view: View) {
            addWindowView2Window(view)
        }

        fun setClazz(clazz: Class<*>?) {
            this@FloatingServer.clazz = clazz
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mWindowManager.removeView(mWindowView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}