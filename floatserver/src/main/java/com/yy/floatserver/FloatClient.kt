package com.yy.floatserver

import android.content.Context
import android.view.View


/**
 * Created by yy on 2020/6/13.
 * function: 悬浮窗
 */
class FloatClient private constructor(private val builder: Builder) : FloatHelper {

    private var view: View? = null

    private var clazz: Class<*>? = null

    private var floatServer: IFloatWindowHandler? = null

    private var context: Context? = null

    private var enableDefaultPermissionDialog = true

    private var callback: IFloatPermissionCallback? = null

    init {
        view = builder.view
        clazz = builder.clazz
        context = builder.context
        callback = builder.callback
        enableDefaultPermissionDialog = builder.enableDefaultPermissionDialog
        checkParam()
    }

    /**
     * 校验参数
     */
    private fun checkParam() {
        context ?: throw IllegalArgumentException("context should not be null....")
        floatServer = FloatProxy(builder)
    }

    class Builder {
        internal var view: View? = null
        internal var clazz: Class<*>? = null
        internal var context: Context? = null
        internal var enableDefaultPermissionDialog = false
        internal var callback: IFloatPermissionCallback? = null

        fun with(context: Context) = apply {
            this.context = context
        }

        /**
         * 调价自定义弹窗样式
         */
        fun addView(view: View) = apply {
            this.view = view
        }

        /**
         * 设置跳转目标
         */
        fun setClickTarget(clazz: Class<*>?) = apply {
            this.clazz = clazz
        }

        /**
         * 悬浮窗权限申请结构回调
         */
        fun addPermissionCallback(callback: IFloatPermissionCallback) = apply {
            this.callback = callback
        }

        /**
         * 是否使用默认弹窗样式，建议使用自己的样式，默认弹窗只是为了展示
         */
        fun enableDefaultPermissionDialog(enable: Boolean) = apply {
            this.enableDefaultPermissionDialog = enable
        }

        fun build(): FloatHelper {
            return FloatClient(this)
        }
    }

    /**
     * 申请权限
     */
    override fun requestPermission() {
        floatServer?.requestPermission()
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