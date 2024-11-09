package app.mediatech.aggrabandhu.utils

import android.content.Context

class SharedPrefManager(private val context : Context) {

    // SharedPreferences instance
    private val sharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    // Editor to make changes
    private val editor = sharedPreferences.edit()

    /*  ------------------------------------------------------------------*/
    fun saveMemberID(memberId : Int){
        editor.putInt("memberID",memberId)
        editor.apply()
    }
    fun getMemberID(): Int {
        return sharedPreferences.getInt("memberID", 0)
    }

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
    fun saveMarriageDate(marriageDate : String){
        editor.putString("marriageDate",marriageDate)
        editor.apply()
    }
    fun getMarriageDate(): String? {
        return sharedPreferences.getString("marriageDate", "")
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
    fun saveLoginStatus(isLogIn : Boolean){
        editor.putBoolean("loginStatus",isLogIn)
        editor.apply()
    }
    fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean("loginStatus", false)
    }
    fun logOut() {
        editor.clear()// Commit the changes
        editor.apply()  // or editor.commit()
    }

    /*  ------------------------------------------------------------------*/
    fun savProfileUrl(url : String){
        editor.putString("profileUrl",url)
        editor.apply()
    }
    fun getProfileUrl(): String? {
        return sharedPreferences.getString("profileUrl", "")
    }

}