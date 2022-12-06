package com.binatebits.food_reccomendation_system.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import com.binatebits.food_reccomendation_system.R
import com.binatebits.food_reccomendation_system.application.App.Companion.prefs
import com.binatebits.food_reccomendation_system.base.BaseActivity
import com.binatebits.food_reccomendation_system.databinding.ActivityLoginBinding
import com.binatebits.food_reccomendation_system.util.DisplayBanner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var showPassword = false
    override fun initView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {

    }

    override fun initListener() {
    binding.btnLogin.setOnClickListener {
        if(validate()) {
            prefs?.userName = binding.etUserName.text.toString()
            prefs?.password = binding.etPassword.text.toString()
            prefs?.isUserLogin  = true
            moveToNext(BMIActivity::class.java, finishCurrent = false, clearStack = false)
        }

}
    }

    private fun validate() : Boolean
    {
        if(binding.etUserName.text.isEmpty())
        {

            DisplayBanner.info(this , resources.getString(R.string.info),resources.getString(R.string.empty_username))

            return false
        }
        else if(binding.etPassword.text.isEmpty())
        {
            DisplayBanner.info(this , resources.getString(R.string.info),resources.getString(R.string.empty_password))

            return false
        }

        return true
    }

    private fun imgHideListener()
    {
        binding.imgHide.setOnClickListener {

            if (showPassword) {
                showPassword = false
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.imgHide.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)

            } else {
                showPassword = true
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.imgHide.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
            }
            binding.etPassword.setSelection(binding.etPassword.length())
        }
    }
}