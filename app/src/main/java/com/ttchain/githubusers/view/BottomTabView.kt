package com.ttchain.githubusers.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.ttchain.githubusers.R
import kotlinx.android.synthetic.main.view_bottom_tab.view.*

class BottomTabView : ConstraintLayout {

    private var onItemClickListener: ((status: BottomTabState) -> Unit)? = null

    fun setOnItemClickListener(l: (status: BottomTabState) -> Unit) {
        onItemClickListener = l
    }

    fun setItemClick(status: BottomTabState) {
        onItemClickListener?.invoke(status)
    }

    private lateinit var currentPosition: BottomTabState

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_bottom_tab, this, true)

        buttonMain.setOnClickListener(tabButtonClickListener)
        buttonUser.setOnClickListener(tabButtonClickListener)
    }

    private val tabButtonClickListener = OnClickListener { view ->
        if (view.isSelected) {
            return@OnClickListener
        }

        val status: BottomTabState = when (view.id) {
            R.id.buttonMain -> BottomTabState.MAIN
            R.id.buttonUser -> BottomTabState.USER
            else -> return@OnClickListener
        }

        onItemClickListener?.invoke(status)
    }

    fun setTabPosition(status: BottomTabState) {
        currentPosition = status
        val view = when (status) {
            BottomTabState.MAIN -> buttonMain
            BottomTabState.USER -> buttonUser
        }
        buttonMain.isSelected = false
        buttonUser.isSelected = false
        view.isSelected = true
    }
}