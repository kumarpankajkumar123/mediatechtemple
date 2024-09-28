package app.mtt.aggrabandhu.dashboard.sideNavigation.allMembers

data class AllMembersData(
    val totalItems : Int,
    val totalPages : Int,
    val currentPage : Int,
    val data : List<AllMemberData>
)

data class AllMemberData (
    val id : Int,
    val reference_id : String,
    val gotra: String,
    val name : String,
    val father_name: String,
    val mother_name: String,
    val dob: String,
    val email: String,
    val marital_status: String,
    val spouse_name: String,
    val mobile_no: String,
    val address: String,
    val district: String,
    val state: String,
    val pincode: String,
    val profession: String,
    val aadhar_no: String,
    val id_file: String,
    val id_no: String,
    val id_type: String,
    val status: String,
    val aadharUrl: String,
    val profileUrl: String,
    val rulesAccepted: Boolean,
    val disease: Boolean,
    val diseaseFile: String,
    val tahsil: String,
    val updatedAt: String,
    val createdAt: String,
    val nominees : List<NomineesData>
)

data class NomineesData(
    val nominee : String,
    val relationship : String,
    val nominee2 : String,
    val relationship2 : String,
)