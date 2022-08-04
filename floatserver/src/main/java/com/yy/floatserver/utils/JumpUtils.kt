package com.yy.floatserver.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

/**
 * Created by yy on 2020/6/13.
 * function: 跳转工具类
 */
object JumpUtils {

    fun jump(context: Context, clazz: Class<*>?) {
        clazz ?: return
        var intent = Intent(context, clazz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        var pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, 0)
        }
        try {
            pendingIntent.send()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}