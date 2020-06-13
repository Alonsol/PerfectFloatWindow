package com.yy.floatserver.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object JumpUtils {

    fun jump(context: Context, clazz: Class<*>?) {
        clazz?:return
        var intent = Intent(context, clazz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        var pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        try {
            pendingIntent.send()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}