package com.yy.floatserver

import android.content.Context
import android.view.View
import java.lang.IllegalArgumentException


/**
 * Created by yy on 2020/6/13.
 * function: 悬浮窗
 */
class FloatClient private constructor(private val builder: Builder) :FloatHelper{

    private var view: View? = null

    private var clazz: Class<*>? = null

    private var floatServer: IFloatWindowHandler? = null

    private var context:Context?=null

    init {
        view = builder.view
        clazz = builder.clazz
        context = builder.context
        checkParam()
    }

    private fun checkParam() {
        context?:throw IllegalArgumentException("context should not be null....")
        floatServer = FloatProxy(builder)
    }

    class Builder {
        internal var view: View? = null
        internal var clazz: Class<*>? = null
        internal var context: Context? = null

        fun with(context: Context)= apply{
            this.context = context
        }

        fun addView(view: View) = apply {
            this.view = view
        }

        fun setClickTarget(clazz: Class<*>?) = apply {
            this.clazz = clazz
        }

        fun build(): FloatHelper {
            return FloatClient(this)
        }
    }

    /**
     * 显示悬浮窗
     */
    override fun show() {
        floatServer?.showFloat()
    }

    /**
     * 隐藏悬浮窗
     */
    override fun dismiss() {
        floatServer?.dismissFloat()
    }

    /**
     * 释放资源并关闭悬浮窗
     */
    override fun release() {
        floatServer?.releaseResource()
    }

}