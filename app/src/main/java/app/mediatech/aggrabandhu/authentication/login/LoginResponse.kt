package app.mediatech.aggrabandhu.authentication.login

data class LoginResponse(
    val token: String,
    val userid: Int,
    val username: String,
    val profileUrl: String
)