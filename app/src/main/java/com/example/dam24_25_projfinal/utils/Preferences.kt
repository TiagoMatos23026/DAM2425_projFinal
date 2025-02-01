package com.example.dam24_25_projfinal.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object Preferences {
    private const val KEY_PERMISSIONS_GRANTED = "permissionsGranted"

    private const val KEY_TERMS = "acceptedTerms"

    private const val PREFS = "prefsFile"

    private const val USER_ID = "user"

    private const val KEY_TOKEN = "token"

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

    fun saveToken(context: Context, token: String) {
        getSharedPreferences(context)
            .edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun clearToken(context: Context) {
        getSharedPreferences(context)
            .edit()
            .remove(KEY_TOKEN)
            .apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_TOKEN, null)
    }

    fun setUser(context: Context, user: String){
        getSharedPreferences(context)
            .edit()
            .putString(USER_ID, user)
            .apply()
    }

    fun getUser(context: Context): String?{
        return getSharedPreferences(context).getString(USER_ID, null)
    }

    fun arePermissionsGranted(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_PERMISSIONS_GRANTED, false)
    }

    fun setPermissionsGranted(context: Context, granted: Boolean) {
        getSharedPreferences(context)
            .edit()
            .putBoolean(KEY_PERMISSIONS_GRANTED, granted)
            .apply()
    }
}