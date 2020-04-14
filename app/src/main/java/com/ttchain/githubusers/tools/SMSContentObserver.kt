package com.ttchain.githubusers.tools

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import com.ttchain.githubusers.data.SmsInfo
import timber.log.Timber
import java.lang.Exception

class SMSContentObserver(val context: Context?, handler: Handler?) : ContentObserver(handler) {

    private var messageListener: MessageListener? = null

    override fun onChange(selfChange: Boolean, uri: Uri) {
        var smsInfoList = emptyList<SmsInfo>()
        try {
            smsInfoList = getSmsInfo(context, uri)
        } catch (e: Exception) {
            Timber.w("smsError : ${e.message}")
        }
        if (smsInfoList.isNotEmpty()) {
//            val verifyCode = smsInfoList[0].smsBody?.replace("[^\\d.]".toRegex(), "")
//            verifyCode?.let { messageListener?.onReceived(it) }
            val smsBody = smsInfoList[0].smsBody
            smsBody?.let {
                messageListener?.onReceived(it)
            }
        }
    }

    /**
     * 注意:
     * 該處只用按照時間降序取出第一條即可
     * 這條當然是最新收到的消息
     */
    private fun getSmsInfo(context: Context?, uri: Uri): List<SmsInfo> {
        val smsInfoList: ArrayList<SmsInfo> = ArrayList()
        findSms(
            context,
            uri,
            arrayOf("_id", "address", "person", "body", "date", "type"),
            smsInfoList
        )
        return smsInfoList
    }

    private fun findSms(
        context: Context?,
        uri: Uri,
        projection: Array<String>,
        smsInfoList: ArrayList<SmsInfo>
    ) {
        val cursor: Cursor? =
            context?.contentResolver?.query(uri, projection, null, null, "date desc limit 1")
        val nameColumn: Int? = cursor?.getColumnIndex("person")
        val phoneNumber = cursor?.getColumnIndex("address")
        val smsBodyColumn = cursor?.getColumnIndex("body")
        val dateColumn = cursor?.getColumnIndex("date")
        val typeColumn = cursor?.getColumnIndex("type")

        if (cursor != null) {
            while (cursor.moveToNext()) {
                smsInfoList.add(
                    SmsInfo(
                        smsBodyColumn?.let { cursor.getString(it) },
                        phoneNumber?.let { cursor.getString(it) },
                        dateColumn?.let { cursor.getString(it) },
                        nameColumn?.let { cursor.getString(it) },
                        typeColumn?.let { cursor.getInt(it) }
                    )
                )
            }
            cursor.close()
        }
    }

    fun register(messageListener: MessageListener?) {
        context?.contentResolver?.registerContentObserver(
            Uri.parse("content://sms/"),
            true,
            this
        )
        this.messageListener = messageListener
    }

    fun unRegister() {
        context?.contentResolver?.unregisterContentObserver(this)
        messageListener = null
    }

    interface MessageListener {
        fun onReceived(message: String?)
    }
}