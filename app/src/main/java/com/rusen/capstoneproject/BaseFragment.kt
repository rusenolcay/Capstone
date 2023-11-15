package com.rusen.capstoneproject

import android.content.DialogInterface
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected fun showMessage(message: String?) {
        activity?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
            ?.let { rootView ->
                message?.let { Snackbar.make(rootView, it, 2000).show() }
            }
    }

    protected fun showMessageDialog(
        title: String = getString(R.string.caution),
        message: String,
        positiveButtonText: String? = getString(R.string.yes),
        positiveButtonAction: DialogInterface.OnClickListener? = null,
        negativeButtonText: String? = getString(R.string.no),
        negativeButtonAction: DialogInterface.OnClickListener? = DialogInterface.OnClickListener { dialog, _ ->
            dialog?.dismiss()
        }
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText, positiveButtonAction)
            .setNegativeButton(negativeButtonText, negativeButtonAction)
            .show()
    }
}