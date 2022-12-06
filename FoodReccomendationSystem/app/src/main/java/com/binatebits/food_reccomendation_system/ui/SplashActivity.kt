package com.binatebits.food_reccomendation_system.ui


import android.annotation.SuppressLint
import android.os.Handler
import com.binatebits.food_reccomendation_system.MainActivity
import com.binatebits.food_reccomendation_system.application.App.Companion.prefs
import com.binatebits.food_reccomendation_system.base.BaseActivity
import com.binatebits.food_reccomendation_system.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun initView() {
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
    }

    override fun initData() {
        Handler().postDelayed({
            navigateToNextScreen()
        }, 3000)

    }

    override fun initListener() {

    }

    private fun navigateToNextScreen() {

        if(prefs?.isUserLogin == true)
        {
            moveToNext(MainActivity::class.java, finishCurrent = false, clearStack = false)
        }
        else {
            moveToNext(LoginActivity::class.java, finishCurrent = false, clearStack = false)
        }


    }

}