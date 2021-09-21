package id.smartech.smartnime.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {
    companion object {
        const val PREF_NAME = "LOGIN"
        const val FILTER_SEARCH = "FILTER_SEARCH"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveFilter(filter: String) {
        editor.putString(FILTER_SEARCH, filter)
        editor.commit()
    }

    fun getFilter(): String {
        return sharedPreferences.getString(FILTER_SEARCH, "")!!
    }
}