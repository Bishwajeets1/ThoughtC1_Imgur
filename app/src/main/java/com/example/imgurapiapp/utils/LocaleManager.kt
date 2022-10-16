package com.example.imgurapiapp.utils

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.annotation.StringDef
import java.util.*

object LocaleManager {
    private const val LANGUAGE_PREF = "language_pref"
    private const val LANGUAGE_KEY = "language_key"
    const val ENGLISH = "en"
    const val HINDI = "hi"
    const val MARATHI = "mr"
    const val GUJARATI = "gu"
    const val KANNADA = "kn"
    const val TAMIL = "ta"
    const val TELUGU = "te"
    const val COUNTRY_INDIA = "IN"
    val languageArray = arrayOf(HINDI, MARATHI, GUJARATI)

    @JvmStatic
    fun getSupportedLanguages(context: Context): Array<String> {
        return arrayOf(ENGLISH, HINDI)
    }

    /**
     * set current pref locale
     */
    @JvmStatic
    fun setLocale(mContext: Context): Context {
        return updateResources(mContext, getLanguagePref(mContext))
    }

    /**
     * Set new Locale with context
     */
    @JvmStatic
    fun setNewLocale(mContext: Context, @LocaleDef language: String): Context {
        setLanguagePref(mContext, language)
        return updateResources(mContext, language)
    }

    /**
     * Get saved Locale from SharedPreferences
     *
     * @param mContext current context
     * @return current locale key by default return english locale
     */
    @JvmStatic
    fun getLanguagePref(mContext: Context): String {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(LANGUAGE_PREF, MODE_PRIVATE)
        return sharedPreferences.getString(LANGUAGE_KEY, ENGLISH) ?: ENGLISH
    }

    /**
     * set pref key
     */
    private fun setLanguagePref(mContext: Context, localeKey: String) {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(LANGUAGE_PREF, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(LANGUAGE_KEY, localeKey)
        editor.apply()
    }

    /**
     * update resource
     */
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language, COUNTRY_INDIA)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    /**
     * get current locale
     */
    @JvmStatic
    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) config.locales[0] else config.locale
    }

    /**
     * get language query parameter
     */
    @JvmStatic
    fun getLanguageQueryParam(context: Context): String {
        return "?lan=" + getLanguagePref(context)
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @StringDef(ENGLISH, HINDI, MARATHI, GUJARATI)
    annotation class LocaleDef {
        companion object {
            var SUPPORTED_LOCALES = languageArray
        }
    }


    private fun setSystemLanguage(deviceLanguage: String, context: Activity) {
        when (deviceLanguage) {
            HINDI, MARATHI, GUJARATI -> {
                setNewLocale(context, deviceLanguage)
            }
            else -> {
                setNewLocale(context, ENGLISH)
            }
        }
        val intent = context.intent
        intent.putExtra("can_ask_feedback", false)
        intent.putExtra("isLanguageChange", true)
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun getDateFormatForMultilingual(context: Context): String {
        var requiredDateFormat = "dd MMMM"
        when (getLanguagePref(context)) {
            ENGLISH -> {
                requiredDateFormat = "dd MMM"
            }
        }
        return requiredDateFormat
    }

    fun getDateFormatForChatMultilingual(context: Context): String {
        var requiredDateFormat = "dd MMMM, yyyy"
        when (getLanguagePref(context)) {
            ENGLISH -> {
                requiredDateFormat = "dd MMM, yyyy"
            }
        }
        return requiredDateFormat
    }

}