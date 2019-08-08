package com.deep.progressdialogsample

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.deep.progressdialog.ProgressDialog


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgressBar(null)
    }

    fun showProgressBar(@Suppress("UNUSED_PARAMETER") view: View?) {
        val progressDialog = ProgressDialog(this)
        progressDialog.showProgressValue(true)
        progressDialog.setProgressColor(R.color.colorPrimary)
        progressDialog.setMessage("Downloading....")
        progressDialog.showMessageText(true)
        progressDialog.setProgressType(ProgressDialog.HUD)
        progressDialog.setIndeterminate(false)
        progressDialog.incrementProgressBy(50)
        val handler = Handler()
        var progress = 0
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress < 100) {
                    progress++
                    progressDialog.setProgress(progress)
                    handler.postDelayed(this, 60)
                } else {
                    progressDialog.dismissProgressDialog()
                }
            }
        }, 60)
    }

}
