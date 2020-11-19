package com.example.instagramapp

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater

class Loading {
    private var activity : Activity? = null
    private var dialog : AlertDialog? = null

    constructor(myActivity: Activity?) {
        activity = myActivity
    }

    public fun startLoadingActivity(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)

        val inflater : LayoutInflater = activity!!.layoutInflater

        builder.setView(inflater.inflate(R.layout.custom_dialog, null))

        builder.setCancelable(false)

        dialog = builder.create()

        dialog!!.show()
    }

    public fun dismissDialog(){
        dialog!!.dismiss()
    }


}