package app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding

data class SignupResponse(
    val memberAdd: MemberAdd,
    val message: String,
    val role: Role
)

data class Role(
    val createdAt: String,
    val id: Int,
    val member_id: Int,
    val updatedAt: String
)

data class MemberAdd(
    val aadharUrl: String,
    val aadhar_no: String,
    val address: String,
    val createdAt: String,
    val disease: Any,
    val diseaseFile: String,
    val district: String,
    val dob: String,
    val email: String,
    val father_name: String,
    val gotra: String,
    val id: Int,
    val id_file: String,
    val id_no: String,
    val id_type: String,
    val marital_status: String,
    val mobile_no: String,
    val mother_name: String,
    val name: String,
    val password: String,
    val pincode: String,
    val profession: String,
    val profileUrl: String,
    val reference_id: String,
    val rulesAccepted: Any,
    val spouse_name: Any,
    val state: String,
    val status: String,
    val tahsil: Any,
    val updatedAt: String
)