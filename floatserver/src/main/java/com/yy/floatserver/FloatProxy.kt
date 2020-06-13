package com.yy.floatserver


/**
 * Created by yy on 2020/6/13.
 * function: 悬浮窗代理类
 */
class FloatProxy(builder: FloatClient.Builder) : IFloatWindowHandler {

    private var manager: FloatManager = FloatManager(builder)

    override fun showFloat() {
        manager.showFloat()
    }

    override fun dismissFloat() {
        manager.dismissFloat()
    }

    override fun releaseResource() {
        manager.releaseResource()
    }
}