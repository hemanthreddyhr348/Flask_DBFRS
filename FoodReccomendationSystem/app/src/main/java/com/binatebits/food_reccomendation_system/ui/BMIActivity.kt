package com.binatebits.food_reccomendation_system.ui


import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doOnTextChanged
import com.binatebits.food_reccomendation_system.MainActivity
import com.binatebits.food_reccomendation_system.R
import com.binatebits.food_reccomendation_system.application.prefs
import com.binatebits.food_reccomendation_system.base.BaseActivity
import com.binatebits.food_reccomendation_system.databinding.ActivityBmiactivityBinding
import com.binatebits.food_reccomendation_system.util.roundTo
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class BMIActivity : BaseActivity() {
    private lateinit var binding: ActivityBmiactivityBinding
    private var exerciseFrequency = 1.2
    override fun initView() {
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setSpinner()
    }
    override fun initData() {
        setSpinner()
    }

    override fun initListener() {
        setupNextListener()
        setupRadioGroupListener()
        autoCompleteListener()
        binding.etHeight.doOnTextChanged { text, _, _, _ ->
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != 1)
            {
               if(selectedRadioButtonId == R.id.male)
               {
                   calculateCaloriesForMale()
               }
                else
               {
                   calculateCaloriesForFeMale()
               }
            }
        }

        binding.etWeight.doOnTextChanged { text, _, _, _ ->
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != 1)
            {
                if(selectedRadioButtonId == R.id.male)
                {
                    calculateCaloriesForMale()
                }
                else
                {
                    calculateCaloriesForFeMale()
                }
            }
        }

        binding.etAge.doOnTextChanged { text, _, _, _ ->
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != 1)
            {
                if(selectedRadioButtonId == R.id.male)
                {
                    calculateCaloriesForMale()
                }
                else
                {
                    calculateCaloriesForFeMale()
                }
            }
        }

    }

    private fun checkCalories()
    {
        val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        if (selectedRadioButtonId != 1)
        {
            if(selectedRadioButtonId == R.id.male)
            {
                calculateCaloriesForMale()
            }
            else
            {
                calculateCaloriesForFeMale()
            }
        }
    }

    private fun setupNextListener()
    {
        binding.btnNext.setOnClickListener {
            if(validate())
            {
                prefs.calories = binding.tvCaloriesCount.text.toString()
                prefs.height = binding.etHeight.text.toString()
                prefs.weight = binding.etWeight.text.toString()
                prefs.age = binding.etAge.text.toString()
                val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
                if (selectedRadioButtonId != 1)
                {
                    if(selectedRadioButtonId == R.id.male)
                    {
                        prefs.gender = "Male"
                    }
                    else
                    {
                        prefs.gender = "FeMale"
                    }
                }


                moveToNext(MainActivity::class.java, finishCurrent = false, clearStack = false)
            }
        }

    }

    private fun setupRadioGroupListener()
    {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (R.id.male == checkedId)
            {
                calculateCaloriesForMale()
            }
            else
            {
                calculateCaloriesForFeMale()
            }
        }

    }


    private fun calculateCaloriesForMale()
    {
       if(validate())
       {
           binding.layoutCalories.visibility = VISIBLE
           binding.layoutCalories.visibility = VISIBLE
           val bmr = 66.47 + (13.75 * (binding.etWeight.text.toString().toFloat())) + (5.003 * (binding.etHeight.text.toString().toFloat())) - (6.755 * (binding.etAge.text.toString().toFloat()))
           val amr = exerciseFrequency * bmr
           binding.tvCaloriesCount.text = amr.roundTo(2).toString() + " Cal"
           prefs.caloriesCount = amr.toInt().toString()

       }
        else
       {
           binding.layoutCalories.visibility = GONE
       }
    }
    private fun calculateCaloriesForFeMale()
    {
        if(validate())
        {
            binding.layoutCalories.visibility = VISIBLE
            val bmr = 655.1 + (9.563 * (binding.etWeight.text.toString().toFloat())) + (1.850 * (binding.etHeight.text.toString().toFloat())) - (4.676 * (binding.etAge.text.toString().toFloat()))
            val amr = exerciseFrequency * bmr
            binding.tvCaloriesCount.text = amr.roundTo(2).toString() + " Cal"
            prefs.caloriesCount = amr.toInt().toString()

        }
        else
        {
            binding.layoutCalories.visibility = GONE
        }
    }

    private fun validate() : Boolean
    {
         if(binding.etHeight.text.isEmpty()) {
             return  false
        } else if(binding.etWeight.text.isEmpty()) {
             return  false
        }
        else if(binding.etAge.text.isEmpty()) {
             return  false
        }
        return true
    }

    private fun autoCompleteListener()
    {
        binding.filledExposedDropdown.setOnItemClickListener { _, _, position, _ ->

            when(position)
            {
                0 -> {
                 exerciseFrequency = 1.2
                    checkCalories()
                }
                1-> {
                    exerciseFrequency = 1.375
                    checkCalories()
                }
                2-> {
                    exerciseFrequency = 1.55
                    checkCalories()
                }
                3-> {
                    exerciseFrequency = 1.725
                    checkCalories()
                }
                4-> {
                    exerciseFrequency = 1.9
                    checkCalories()
                }

            }
        }

    }

    private  fun setSpinner()
    {
        val adapter = ArrayAdapter(this@BMIActivity, R.layout.list_account_item, getItemList())
        binding.filledExposedDropdown.setAdapter(adapter)

    }

    private fun getItemList(): ArrayList<String> {
        val itemList=ArrayList<String>()
        val items = arrayOf(
            getString(R.string.sedentary_little_or_no_exercise),
            getString(R.string.lightly_active),
            getString(R.string.mod_active),
            getString(R.string.active),
            getString(R.string.very_active)
        )
        itemList.addAll(items)
        return itemList
    }
}