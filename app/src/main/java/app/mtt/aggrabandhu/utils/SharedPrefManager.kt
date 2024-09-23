package app.mtt.aggrabandhu.utils

import android.content.Context

class SharedPrefManager(private val context : Context) {

    // SharedPreferences instance
    private val sharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    // Editor to make changes
    private val editor = sharedPreferences.edit()

    fun saveImageUri(uri : String){
        editor.putString("uri",uri)
        editor.apply()
    }

    fun getImageUri(): String? {
        return sharedPreferences.getString("uri", "")
    }
}