package com.deep.progressdialog

import android.content.Context
import android.content.res.Resources
import android.graphics.ColorFilter
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import org.jetbrains.annotations.NotNull

class ProgressDialog(@NotNull context: Context?) : View(context) {

    private var builder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var progressBar: ContentLoadingProgressBar? = null
    private var message: AppCompatTextView? = null
    private var progressText: AppCompatTextView? = null
    private var showProgressText = true


    init {
        builder = AlertDialog.Builder(context!!)
        val dialogView: View = (context as AppCompatActivity).layoutInflater.inflate(R.layout.progress_dialog, null)
        message = dialogView.findViewById<AppCompatTextView>(R.id.message)
        progressBar = dialogView.findViewById<ContentLoadingProgressBar>(R.id.progress_bar)
        progressText = dialogView.findViewById<AppCompatTextView>(R.id.progress_text)
        progressBar?.isIndeterminate = false
        progressBar?.max = 100

        message?.text = resources.getText(R.string.loading)
        builder?.setView(dialogView)
        builder?.setCancelable(false)
        dialog = builder?.create()

    }

    fun showProgressDialog() {
        if (!dialog!!.isShowing) {
            showProgressValue(showProgressText)
            dialog?.show()
        }
    }

    fun setMessage(@NotNull value: String) {
        message?.text = value
    }

    fun setMessage(@NotNull id: Int) {
        try {
            message?.text = resources.getText(id)
        } catch (e: Resources.NotFoundException) {
            Log.e("ResourceNotFound", e.message)
        }
    }

    fun setProgressColor(@NotNull color: Int) {
        try {
            progressBar?.progressDrawable?.setColorFilter(
                ContextCompat.getColor(context, color),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        } catch (e: Resources.NotFoundException) {
            Log.e("ResourceNotFound", e.message)
        }
    }

    fun setProgressColor(@NotNull color: ColorFilter) {
        progressBar?.progressDrawable?.colorFilter = color
    }

    fun dismissProgressDialog() {
        if (dialog!!.isShowing) {
            setProgress(0)
            dialog?.dismiss()
        }
    }


    fun incrementProgressBy(newProgress: Int) {
        progressBar?.incrementProgressBy(newProgress)
        setProgressText()
    }

    fun setProgress(value: Int) {
        progressBar?.progress = value
        setProgressText()
    }

    private fun setProgressText() {
        if (showProgressText)
            progressText?.text = String.format(resources.getString(R.string.loading_progress), progressBar?.progress)
    }

    fun showProgressValue(show: Boolean) {
        showProgressText = show
        progressText?.visibility = if (show) VISIBLE else GONE

    }


}