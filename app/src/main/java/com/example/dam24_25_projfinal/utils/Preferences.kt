package com.example.dam24_25_projfinal.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object Preferences {

    private const val KEY_TERMS = "acceptedTerms"

    private const val PREFS = "prefsFile"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun termsAccepted(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_TERMS, false)
    }

    fun setTermsAccepted(context: Context, accepted: Boolean) {
        getSharedPreferences(context)
            .edit()
            .putBoolean(KEY_TERMS, accepted)
            .apply()
        Log.d("Preferences", "Terms accepted: $accepted")
    }

}