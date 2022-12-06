package com.binatebits.food_reccomendation_system.util

import android.app.Activity
import com.binatebits.food_reccomendation_system.R
import com.tapadoo.alerter.Alerter

class DisplayBanner {

    companion object
    {
        fun info(activity : Activity, title: String, message : String  )
        {
            Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setBackgroundColorRes(R.color.colorAccent)
                .setIcon(R.drawable.ic_info)
                .setIconSize(com.tapadoo.alerter.R.dimen.alerter_alert_icn_size)
                .show()
        }


        fun error(activity : Activity, title: String, message : String  )
        {
            Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setBackgroundColorRes(R.color.appButtonGradiantStart)
                .setIcon(R.drawable.ic_error)
                .setIconSize(com.tapadoo.alerter.R.dimen.alerter_alert_icn_size)
                .show()
        }

        fun success(activity : Activity, title: String, message : String  )
        {
            Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setBackgroundColorRes(R.color.color_295210)
                .setIcon(R.drawable.ic_success)
                .setIconSize(com.tapadoo.alerter.R.dimen.alerter_alert_icn_size)
                .show()
        }

    }
}