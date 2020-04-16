package com.ttchain.githubusers.tools

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    /**
     * timeStampString in SECONDS.
     */
    @JvmStatic
    fun getTimeFormat(timeStampString: String): String {
        return if (timeStampString.isNotEmpty()) {
            val timeStamp = java.lang.Long.parseLong(timeStampString)
            getFormatTimeInDefaultLocale(timeStamp * 1000L)
        } else {
            ""
        }
    }

    /**
     * time in MILLISECONDS.
     */
    @JvmStatic
    fun getFormatTimeInDefaultLocale(time: Long): String {
        val cal = Calendar.getInstance(Locale.getDefault())
        cal.timeInMillis = time
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        return sdf.format(cal.time)
    }

    /**
     * Timestamp in SECONDS.
     */
    @JvmStatic
    fun getNowTimestamp(): Long {
        val cal = Calendar.getInstance()
        return cal.time.time
    }
}