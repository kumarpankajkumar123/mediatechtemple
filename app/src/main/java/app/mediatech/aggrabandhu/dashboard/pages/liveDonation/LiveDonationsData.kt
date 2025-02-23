package app.mediatech.aggrabandhu.dashboard.pages.liveDonation

data class LiveDonationsData(
    val totalItems : Int,
    val totalPages : Int,
    val currentPage : Int,
    val data : List<LiveDonationData>
)

data class LiveDonationData(
    val id : Int,
    val member_id : Int,
    val total_donation_received : String,
    val min_amount : String,
    val upi_id : String,
    val start_date : String,
    val end_date : String,
    val status : String,
    val bank_detail: String,
    val death_date : String,
    val createdAt : String,
    val updatedAt : String,
    val Member : MemberDetails
)

data class MemberDetails(
    val id : Int,
    val name : String,
    val state : String,
    val district : String,
    val profileUrl : String,
)
data class BankDetail(
    val account_number: String,
    val bank_name: String,
    val ifsc_code: String
)