package com.binatebits.food_reccomendation_system.util

import android.content.Context
import android.content.SharedPreferences
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.AGE
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.CALORIES
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.CALORIES_COUNT
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.DARK_MODE
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.DOB
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.EMAIL
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.GENDER
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.HEIGHT
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.ISLOGGEDIN
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.IS_VERIFIED
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.KUSERID
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.LIGHT_MODE
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.PASSWORD
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.PHONE_NUMBER
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.USERNAME
import com.binatebits.food_reccomendation_system.util.PrefKeys.Companion.WEIGHT


class Prefs (context: Context) {
    private val APPPREFNAME = "REC_SYSTEM"
    private val preferences: SharedPreferences = context.getSharedPreferences(APPPREFNAME , Context.MODE_PRIVATE)




    fun clear()
    {
        preferences.edit().clear().apply()
    }

    var isUserLogin : Boolean
        get() = preferences.getBoolean(ISLOGGEDIN , false)
        set(value) = preferences.edit().putBoolean(ISLOGGEDIN, value).apply()

    var isUserVerified : Boolean
        get() = preferences.getBoolean(IS_VERIFIED , false)
        set(value) = preferences.edit().putBoolean(IS_VERIFIED, value).apply()

    var userID : String
        get() = preferences.getString(KUSERID , "")!!
        set(value) = preferences.edit().putString(KUSERID, value).apply()


    var userName : String
        get() = preferences.getString(USERNAME , "")!!
        set(value) = preferences.edit().putString(USERNAME, value).apply()


    var password : String
        get() = preferences.getString(PASSWORD , "")!!
        set(value) = preferences.edit().putString(PASSWORD, value).apply()

    var userEmail : String
        get() = preferences.getString(EMAIL , "")!!
        set(value) = preferences.edit().putString(EMAIL, value).apply()


    var phoneNumber : String
        get() = preferences.getString(PHONE_NUMBER , "")!!
        set(value) = preferences.edit().putString(PHONE_NUMBER, value).apply()

    var dob : String
        get() = preferences.getString(DOB , "")!!
        set(value) = preferences.edit().putString(DOB, value).apply()

    var darkMode : Boolean
        get() = preferences.getBoolean(DARK_MODE , false)
        set(value) = preferences.edit().putBoolean(DARK_MODE, value).apply()

    var lightMode : Boolean
        get() = preferences.getBoolean(LIGHT_MODE , false)
        set(value) = preferences.edit().putBoolean(LIGHT_MODE, value).apply()

    var calories : String
        get() = preferences.getString(CALORIES,"0.0")!!
        set(value) = preferences.edit().putString(CALORIES, value).apply()
    var caloriesCount : String
        get() = preferences.getString(CALORIES_COUNT,"0.0")!!
        set(value) = preferences.edit().putString(CALORIES_COUNT, value).apply()

    var height : String
        get() = preferences.getString(HEIGHT,"0.0")!!
        set(value) = preferences.edit().putString(HEIGHT, value).apply()

    var weight : String
        get() = preferences.getString(WEIGHT,"0.0")!!
        set(value) = preferences.edit().putString(WEIGHT, value).apply()

    var gender : String
        get() = preferences.getString(GENDER,"Male")!!
        set(value) = preferences.edit().putString(GENDER, value).apply()

    var age : String
        get() = preferences.getString(AGE,"0")!!
        set(value) = preferences.edit().putString(AGE, value).apply()


}