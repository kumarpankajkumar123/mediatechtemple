package app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations

data class MyDonationData(
    val currentPage: Int,
    val `data`: List<MyDonation>,
    val totalItems: Int,
    val totalPages: Int
)

data class MyDonation(
    val Member: Member,
    val amount: String,
    val createdAt: String,
    val donation_date: String,
    val donation_id: Int,
    val id: Int,
    val member_id: Int,
    val payment_method: String,
    val status: String,
    val to: To,
    val transaction_file: String,
    val transaction_id: String,
    val updatedAt: String
)

data class To(
    val district: String,
    val email: String,
    val id: Int,
    val mobile_no: String,
    val profileUrl: String,
    val name: String,
    val state: String
)

data class Member(
    val district: String,
    val email: String,
    val id: Int,
    val name: String,
    val state: String
)