package app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding

data class PostalData(
    val Message: String,
    val PostOffice: List<PostOffice>,
    val Status: String
)

data class PostOffice(
    val Country: String,
    val District: String,
    val Name: String,
    val Pincode: String,
    val State: String
)
