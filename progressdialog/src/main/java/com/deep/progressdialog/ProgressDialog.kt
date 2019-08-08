package com.deep.progressdialog

import android.content.Context
import android.content.res.Resources
import android.graphics.ColorFilter
import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import org.jetbrains.annotations.NotNull


class ProgressDialog(@NotNull context: Context?) : View(context) {
    companion object {
        const val HORIZONTAL: Int = 0
        const val HUD: Int = 1
    }

    private var builder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var progressBar: ContentLoadingProgressBar? = null
    private var message: AppCompatTextView? = null
    private var progressText: AppCompatTextView? = null
    private var showProgressText = true
    private var showMessageText = true
    private var imageView: AppCompatImageView? = null
    private var progressBarType = HORIZONTAL
    private var indeterminate = false


    init {
        /*val outValue = TypedValue()
        context?.theme?.resolveAttribute(
            androidx.appcompat.R.attr.alertDialogTheme,
            outValue,
            true
        )
        val value = outValue.resourceId*/
        builder = AlertDialog.Builder(context!!/*,value*/)
        val dialogView: View = (context as AppCompatActivity).layoutInflater.inflate(R.layout.progress_dialog, null)
        message = dialogView.findViewById(R.id.message)
        progressBar = dialogView.findViewById(R.id.progress_bar)
        progressText = dialogView.findViewById(R.id.progress_text)
        progressBar?.isIndeterminate = indeterminate
        progressBar?.max = 100
        imageView = dialogView.findViewById<AppCompatImageView>(R.id.spinnerImageView)
        val spinner = imageView?.background as AnimationDrawable
        spinner.start()
        message?.text = resources.getText(R.string.loading)
        builder?.setView(dialogView)
        builder?.setCancelable(false)
        dialog = builder?.create()

    }

    fun showProgressDialog() {
        if (!dialog!!.isShowing) {
            if (progressBarType == HORIZONTAL) {
                imageView?.visibility = GONE
                progressBar?.visibility = VISIBLE
            } else if (progressBarType == HUD) {
                imageView?.visibility = VISIBLE
                progressBar?.visibility = GONE
                dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                showProgressValue(false)
                showMessageText(false)
            }
            showProgressValue(showProgressText)
            dialog?.show()
        }
    }

    fun setMessage(@NotNull value: String) {
        if (progressBarType == HORIZONTAL) {
            if (showMessageText) {
                message?.text = value
            }
        }
    }

    fun setMessage(@NotNull id: Int) {
        if (progressBarType == HORIZONTAL) {
            if (showMessageText) {
                try {
                    message?.text = resources.getText(id)
                } catch (e: Resources.NotFoundException) {
                    Log.e("ResourceNotFound", e.message)
                }
            }
        }
    }

    fun setProgressColor(@NotNull color: Int) {
        if (progressBarType == HORIZONTAL) {
            try {
                progressBar?.progressDrawable?.setColorFilter(
                    ContextCompat.getColor(context, color),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                progressBar?.indeterminateDrawable?.setColorFilter(
                    ContextCompat.getColor(context, color),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            } catch (e: Resources.NotFoundException) {
                Log.e("ResourceNotFound", e.message)
            }
        }
    }


    fun setProgressColor(@NotNull color: ColorFilter) {
        if (progressBarType == HORIZONTAL) {
            progressBar?.progressDrawable?.colorFilter = color
            progressBar?.indeterminateDrawable?.colorFilter = color
        }
    }

    fun dismissProgressDialog() {
        if (dialog!!.isShowing) {
            setProgress(0)
            dialog?.dismiss()
        }
    }


    fun incrementProgressBy(newProgress: Int) {
        showProgressDialog()
        if (progressBarType == HORIZONTAL) {
            if (!indeterminate) {
                progressBar?.incrementProgressBy(newProgress)
                setProgressText()
            } else {
                progressBar?.isIndeterminate = indeterminate
                showProgressValue(false)
            }


        }
    }

    fun setProgress(value: Int) {
        showProgressDialog()
        if (progressBarType == HORIZONTAL && !indeterminate) {
            if (!indeterminate) {
                progressBar?.progress = value
                setProgressText()
            } else {
                progressBar?.isIndeterminate = indeterminate
                showProgressValue(false)
            }
        }
    }


    private fun setProgressText() {
        if (showProgressText)
            progressText?.text =
                String.format(resources.getString(R.string.loading_progress), progressBar?.progress)

    }

    fun showProgressValue(show: Boolean) {
        if (progressBarType == HORIZONTAL) {
            showProgressText = show
            progressText?.visibility = if (show) VISIBLE else GONE
        }

    }

    fun showMessageText(show: Boolean) {
        showMessageText = show
            message?.visibility = if (show) VISIBLE else GONE

    }

    fun setProgressType(type: Int) {
        progressBarType = type
    }

    fun setIndeterminate(value: Boolean) {
        indeterminate = value
    }


}