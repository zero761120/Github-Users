package com.ttchain.githubusers

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal

fun <T> Observable<T>.toMain(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun BigDecimal.isZero(): Boolean {
    return this.toDouble() == 0.0
}

fun FragmentManager.addDialog(fragment: Fragment, tag: String) {
    if (this.findFragmentByTag(tag)?.isAdded == true) {
        return
    }
    commit(true) {
        add(fragment, tag)
    }
}

fun AppCompatActivity.changeFragment(container: Int, fragment: Fragment) {
    supportFragmentManager.commit(true) {
        replace(container, fragment)
    }
}

fun AppCompatActivity.addFragment(container: Int, fragment: Fragment) {
    supportFragmentManager.commit(true) {
        add(container, fragment)
    }
}

fun Fragment.addFragment(container: Int, fragment: Fragment) {
    childFragmentManager.commit(true) {
        add(container, fragment)
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun String.performCopyString(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.primaryClip = ClipData.newPlainText(null, this)
    context.showToast("Copied")
}

/**
 * 關閉鍵盤
 */
fun Activity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var focusView = this.currentFocus
    if (focusView == null)
        focusView = View(this)
    inputMethodManager.hideSoftInputFromWindow(focusView.windowToken, 0)
}

/**
 * 開啟鍵盤
 */
fun View.showKeyboard() {
    (this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
        this@showKeyboard,
        InputMethodManager.SHOW_IMPLICIT
    )
}

/**
 * 載入html文字
 */
@Suppress("DEPRECATION")
fun TextView.html(html: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)!!
    } else {
        Html.fromHtml(html)
    }
}

/**
 * 將圖片放入系統相簿
 */
fun Activity.addImageToGallery(fileName: String, filePath: String) {
    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues().apply {
        put(MediaStore.Images.Media.TITLE, fileName)
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000)
        put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis() / 1000)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.DATA, filePath)
    })
}