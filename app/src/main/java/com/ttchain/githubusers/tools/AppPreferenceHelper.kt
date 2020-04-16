package com.ttchain.githubusers.tools

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class AppPreferenceHelper(private val preference: SharedPreferences) {

    companion object {
        private const val keyHost = "host"
        private const val keyAccount = "account"
        private const val keyPassword = "password"
        private const val keyAndroidId = "androidId"
    }

    private var gson: Gson = GsonBuilder().create()

//    /**
//     * 儲存使用者資料
//     */
//    var userData: UserData?
//        set(value) = preference.edit { putString(keyUserData, gson.toJson(value)) }
//        get() {
//            val jsonString = preference.getString(keyUserData, "")
//            return if (jsonString == null || jsonString.isEmpty()) {
//                null
//            } else {
//                try {
//                    gson.fromJson(jsonString, UserData::class.java)
//                } catch (error: Exception) {
//                    null
//                }
//            }
//        }

    /**
     * 清除使用者設置
     */
    fun clearToken() {
        preference.edit {
            remove(keyHost)
            remove(keyAccount)
            remove(keyPassword)
            remove(keyAndroidId)
        }
    }

    /**
     * 儲存 host
     */
    var userHost: String
        set(value) = preference.edit { putString(keyHost, Gzip.compress(value)) }
        get() = Gzip.decompress(preference.getString(keyHost, "") ?: "")

    /**
     * 儲存 account
     */
    var userAccount: String
        set(value) = preference.edit { putString(keyAccount, Gzip.compress(value)) }
        get() = Gzip.decompress(preference.getString(keyAccount, "") ?: "")

    /**
     * 儲存 password
     */
    var userPassword: String
        set(value) = preference.edit { putString(keyPassword, Gzip.compress(value)) }
        get() = Gzip.decompress(preference.getString(keyPassword, "") ?: "")

//    /**
//     * 取得Android Id
//     */
//    @SuppressLint("HardwareIds")
//    fun getAndroidId(context: Context): String {
//        var androidId: String = preference.getString(keyAndroidId, "") ?: ""
//        if (androidId.isBlank()) {
//            androidId =
//                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
//            preference.edit { putString(keyAndroidId, androidId) }
//        }
//        return androidId
//    }
}