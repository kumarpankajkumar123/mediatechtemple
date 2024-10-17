package app.mtt.aggrabandhu.authentication.login

data class LoginResponse(
    val token: String,
    val userid: Int,
    val username: String
)