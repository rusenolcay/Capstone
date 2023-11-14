package com.rusen.capstoneproject

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected fun showMessage(message: String?) {
        activity?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
            ?.let { rootView ->
                message?.let { Snackbar.make(rootView, it, 2000).show() }
            }
    }
}