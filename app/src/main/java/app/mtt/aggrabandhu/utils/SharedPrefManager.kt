package app.mtt.aggrabandhu.utils

import android.content.Context
import app.mtt.aggrabandhu.dashboard.pages.profile.shareText

class SharedPrefManager(private val context : Context) {

    // SharedPreferences instance
    private val sharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    // Editor to make changes
    private val editor = sharedPreferences.edit()

    /*  ------------------------------------------------------------------*/
    fun saveReferenceID(referenceID : String){
        editor.putString("referenceID",referenceID)
        editor.apply()
    }
    fun getReferenceID(): String? {
        return sharedPreferences.getString("referenceID", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveFullName(referenceID : String){
        editor.putString("fullName",referenceID)
        editor.apply()
    }
    fun getFullName(): String? {
        return sharedPreferences.getString("fullName", "")
    }

    /*  ------------------------------------------------------------------*/
    fun savePhone(referenceID : String){
        editor.putString("phone",referenceID)
        editor.apply()
    }
    fun getPhone(): String? {
        return sharedPreferences.getString("phone", "")
    }

    /*  ------------------------------------------------------------------*/
    fun savePassword(referenceID : String){
        editor.putString("password",referenceID)
        editor.apply()
    }
    fun getPassword(): String? {
        return sharedPreferences.getString("password", "")
    }

    /* ---------------------------------------  -------------------------*/
    fun saveProfileImageUri(uri : String){
        editor.putString("uri",uri)
        editor.apply()
    }
    fun getProfileImageUri(): String? {
        return sharedPreferences.getString("uri", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveFatherName(referenceID : String){
        editor.putString("FatherName",referenceID)
        editor.apply()
    }
    fun getFatherName(): String? {
        return sharedPreferences.getString("FatherName", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveMotherName(referenceID : String){
        editor.putString("MotherName",referenceID)
        editor.apply()
    }
    fun getMotherName(): String? {
        return sharedPreferences.getString("MotherName", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveGotra(referenceID : String){
        editor.putString("Gotra",referenceID)
        editor.apply()
    }
    fun getGotra(): String? {
        return sharedPreferences.getString("Gotra", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveMarital(referenceID : String){
        editor.putString("Marital",referenceID)
        editor.apply()
    }
    fun getMarital(): String? {
        return sharedPreferences.getString("Marital", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveSpouseName(referenceID : String){
        editor.putString("spouseName",referenceID)
        editor.apply()
    }
    fun getSpouseName(): String? {
        return sharedPreferences.getString("spouseName", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveDOB(referenceID : String){
        editor.putString("DOB",referenceID)
        editor.apply()
    }
    fun getDOB(): String? {
        return sharedPreferences.getString("DOB", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveProfession(referenceID : String){
        editor.putString("profession",referenceID)
        editor.apply()
    }
    fun getProfession(): String? {
        return sharedPreferences.getString("profession", "")
    }

    /*  ------------------------------------------------------------------*/
    fun saveLoginStatus(userid : String){
        editor.putString("login",userid)
        editor.apply()
    }
    fun getLoginStatus(): String? {
        return sharedPreferences.getString("login", "")
    }
    fun logOut() {
        editor.clear()
    }

}