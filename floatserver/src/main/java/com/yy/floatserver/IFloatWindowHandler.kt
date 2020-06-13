package com.yy.floatserver

interface IFloatWindowHandler {

    /**
     * 申请权限
     */
    fun requestPermission()

    /**
     * 展示悬浮窗
     */
    fun showFloat()

    /**
     * 隐藏悬浮窗
     */
    fun dismissFloat()

    /**
     * 隐藏悬浮窗并关闭服务
     */
    fun releaseResource()


}