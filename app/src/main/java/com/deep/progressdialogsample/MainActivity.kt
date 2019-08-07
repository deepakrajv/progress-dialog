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

    fun showProgressBar(view: View?) {
        val progressDialog = ProgressDialog(this)
        progressDialog.showProgressDialog()
        progressDialog.showProgressValue(true)
        //progressDialog?.showProgressDialog()
        //progressDialog.incrementProgressBy(50)
        //if (newProgress == 100) progressDialog?.dismissProgressDialog()
        progressDialog.setProgressColor(R.color.colorPrimary)
        progressDialog.setMessage("Downloading....")
        //progressDialog.showProgressValue(false)
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
