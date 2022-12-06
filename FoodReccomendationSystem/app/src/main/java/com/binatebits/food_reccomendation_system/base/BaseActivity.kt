package com.binatebits.food_reccomendation_system.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity(), BaseActivityListener {

    private var activity: BaseActivity? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initView()
        initData()
        initListener()
        activity = this
    }
    abstract fun initView()

    abstract fun initData()

    abstract fun initListener()

    override fun moveToNext(activityName: Class<*>, finishCurrent: Boolean, clearStack: Boolean) {
        val intent = Intent(this, activityName)
        if (clearStack)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        if (finishCurrent)
            finish()
    }

    override fun moveToNext(
        activityName: Class<*>,
        bundle: Bundle?,
        finishCurrent: Boolean,
        clearStack: Boolean,
        data: Uri?,
        action: String?
    ) {
        val intent = Intent(this, activityName)
        bundle?.let { intent.putExtras(it) }
        if (data != null) intent.data = data
        if (action != null) intent.action = action
        if (clearStack)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        if (finishCurrent)
            finish()
        startActivity(intent)
    }

    override fun addFragment(
        fragment: Fragment,
        container: Int,
        tag: String,
        addReplace: Boolean,
        addToBackStack: Boolean
    ) {
        val manager = supportFragmentManager

        // Begin the fragment transition using support fragment manager
        val transaction = manager.beginTransaction()

        //add animation
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        // Add/Replace the fragment on container
        if (addReplace)
            transaction.add(container, fragment, tag)
        else transaction.replace(container, fragment, tag)
        //Add to back stack
        if (addToBackStack)
            transaction.addToBackStack(null)

        // Finishing the transition
        transaction.commit()
    }

    override fun backPressed() {
        onBackPressed()
    }

    override fun toolbarPrimaryTitle(title: String) {

    }

    override fun toolbarSecondaryTitle(title: String) {

    }

    override fun finishCurrent() {
        finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        applyOverrideConfiguration(Configuration())
        super.attachBaseContext(newBase)
    }



}