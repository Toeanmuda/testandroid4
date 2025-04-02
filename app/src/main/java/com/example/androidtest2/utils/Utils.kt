package com.example.androidtest2.utils

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.LayerDrawable
import com.google.android.material.badge.BadgeDrawable


object Utils {
    fun saveToken(context: Context, token: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("jwt_token", null)
    }

}