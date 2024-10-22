package app.mediatech.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations

data class ReceivedDonationsData(
    val totalItems : Int,
    val totalPages : Int,
    val currentPage : Int,
    val data : List<ReceivedDonationData>
)

data class ReceivedDonationData(
    val id : Int,
    val member_id : Int,
    val donation_id : Int,
    val amount : String,
    val bank_detail: String,
    val donation_date : String,
    val transaction_id : String,
    val payment_method : String,
    val status : String,
    val createdAt : String,
    val updatedAt : String,
    val Member : MemberDetails
)

data class MemberDetails(
    val id : Int,
    val name : String,
    val state : String,
    val district : String,
)
//val profileUrl : String,
