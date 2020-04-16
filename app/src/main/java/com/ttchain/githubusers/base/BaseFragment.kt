package com.ttchain.githubusers.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected abstract val layoutId: Int
    protected abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    fun onShowLoading() {
        activity?.let {
            if (it is BaseActivity) {
                it.onShowLoading()
            }
        }
    }

    fun onHideLoading() {
        activity?.let {
            if (it is BaseActivity) {
                it.onHideLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onHideLoading()
    }
}