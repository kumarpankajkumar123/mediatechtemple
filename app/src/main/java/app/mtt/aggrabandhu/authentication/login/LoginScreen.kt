package app.mtt.aggrabandhu.authentication.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.utils.CircularImage
import app.mtt.aggrabandhu.utils.CustomButton
import app.mtt.aggrabandhu.utils.LoadingAlertDialog
import app.mtt.aggrabandhu.utils.PasswordTextFieldWithIcons
import app.mtt.aggrabandhu.utils.TextFieldWithIcons
import es.dmoral.toasty.Toasty

@Composable
fun LoginScreen (navController: NavController?= null) {
    val context = LocalContext.current

    val loginViewmodel : LoginViewmodel = hiltViewModel()

    val loginResponseCode = loginViewmodel.loginResponseCode.collectAsState()
    val loginResponse by loginViewmodel.loginResponse.collectAsState()

    val phone = loginViewmodel.mobileNumber.collectAsState()
    val password = loginViewmodel.password.collectAsState()

    val showProgress = remember { mutableStateOf(false) }

    if (loginResponseCode.value != 0) {
        if (loginResponseCode.value  == 200) {
            showProgress.value = false
            if (!loginViewmodel.isLogin) {
                loginViewmodel.isLogin = true
                Log.d("Login", "Login ${loginResponse.userid}")

                navController?.navigate("dashboard_screen") {
                    popUpTo(navController.graph.startDestinationId){
                        inclusive = true
                    }

                }
            }
        } else {
            showProgress.value = false
            Toasty.error(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            Log.d("Login", "Invalid Credentials")
        }
    }

    if (showProgress.value) {
        LoadingAlertDialog()
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.textured_bg), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop, // Adjust content scaling as needed
            modifier = Modifier.fillMaxSize(), // Ensure the image fills the entire screen
            alpha = 0.3f
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // below is the composable for image.
            CircularImage(
                size = 300.dp,
                painter = painterResource(id = R.drawable.png_logo)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Heading Login
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.login_to_continue),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(50.dp))

            /* ------------------- Login Username ----------------------- */
            TextFieldWithIcons(
                "Phone Number",
                "Enter your Phone Number",
                10,
                KeyboardType.Phone,
                Icons.Filled.Call
            ) {
                loginViewmodel.onPhoneTextChanged(it)
            }

            /* ------------------- Password ----------------------- */
            PasswordTextFieldWithIcons("Password", "Enter Password") {
                loginViewmodel.onPassTextChanged(it)
            }

            Spacer(modifier = Modifier.height(10.dp))

            /* ---------- Login Button -------------- */
            CustomButton("Login", colorResource(id = R.color.orange)) {
                if (phone.value.length < 10) {
                    Toasty.error(context, "Please enter Phone Number", Toast.LENGTH_SHORT).show()
                } else if (password.value.isEmpty()) {
                    Toasty.error(context, "Please enter password", Toast.LENGTH_SHORT).show()
                } else {
                    showProgress.value = true
//                    navController?.navigate("dashboard_screen")
                    loginViewmodel.login(context)
//                    Toasty.success(context, "${phone.value} - ${password.value}", Toast.LENGTH_SHORT).show()
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "",//Forget Phone Number?
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.End,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Forget Password?",
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.End,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            CustomButton(text = "New User? Signup now", colorResource(id = R.color.green)) {
                navController?.navigate("signup_screen")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    LoginScreen()
}
