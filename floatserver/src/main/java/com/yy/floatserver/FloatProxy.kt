package com.yy.floatserver


/**
 * Created by yy on 2020/6/13.
 * function: 悬浮窗代理类
 */
class FloatProxy(builder: FloatClient.Builder) : IFloatWindowHandler {

    private var manager: FloatManager = FloatManager(builder)

    /**
     * 申请权限
     */
    override fun requestPermission() {
        manager.requestPermission()
    }

    /**
     * 显示悬浮窗
     */
    override fun showFloat() {
        manager.showFloat()
    }

    /**
     * 隐藏悬浮窗
     */
    override fun dismissFloat() {
        manager.dismissFloat()
    }


    /**
     * 隐藏悬浮窗并关闭服务
     */
    override fun releaseResource() {
        manager.releaseResource()
    }

}