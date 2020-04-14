package com.ttchain.githubusers.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ttchain.githubusers.R
import kotlinx.android.synthetic.main.dialog_toast.view.*

class ToastDialog : BottomSheetDialogFragment() {

    companion object {
        private const val keyTitle = "title"
        private const val keyTitleColor = "titleColor"
        private const val keyTitleIcon = "titleIcon"
        private const val keyMsg = "msg"

        fun newInstance(title: String, msg: String, titleColor: Int, titleIcon: Int): ToastDialog {
            return ToastDialog().apply {
                arguments = bundleOf(
                    keyTitle to title,
                    keyTitleColor to titleColor,
                    keyTitleIcon to titleIcon,
                    keyMsg to msg
                )
            }
        }
    }

    private lateinit var mBehavior: BottomSheetBehavior<View>
    private val timer = object : CountDownTimer(3000, 3000) {
        override fun onFinish() {
            dismissAllowingStateLoss()
        }

        override fun onTick(millisUntilFinished: Long) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ToastDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        View.inflate(context, R.layout.dialog_toast, null).apply {
            dialog.setContentView(this)
            tvName.apply {
                text = arguments?.getString(keyTitle)
                setTextColor(
                    ContextCompat.getColor(
                        dialog.context,
                        arguments?.getInt(keyTitleColor) ?: R.color.white
                    )
                )
                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    arguments?.getInt(keyTitleIcon)
                        ?: 0, 0, 0, 0
                )
            }
            tvMsg.text = arguments?.getString(keyMsg)
            btnClose.setOnClickListener {
                dismissAllowingStateLoss()
            }
            mBehavior = BottomSheetBehavior.from(parent as View)
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
//        mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        timer.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }
}