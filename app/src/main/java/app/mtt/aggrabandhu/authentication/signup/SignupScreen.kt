package app.mtt.aggrabandhu.authentication.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.utils.CircularImage
import app.mtt.aggrabandhu.utils.CustomButton
import app.mtt.aggrabandhu.utils.PasswordTextFieldWithIcons
import app.mtt.aggrabandhu.utils.TextFieldWithIcons
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import es.dmoral.toasty.Toasty
import kotlin.math.sign

@Composable
fun SignupScreen(navController: NavController) {
    val context = LocalContext.current

    val signUpViewmodel : SignUpViewmodel = hiltViewModel()

    var referenceID = signUpViewmodel.referenceIdFieldState.collectAsState()
    var name = signUpViewmodel.fullNameFieldState.collectAsState()
    var phone = signUpViewmodel.phoneTextState.collectAsState()
    var password = signUpViewmodel.passwordTextState.collectAsState()
    var confirmPassword: String? = ""

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
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(10.dp),
//            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // below is the composable for image.
            CircularImage(size = 250.dp, painter = painterResource(id = R.drawable.png_logo))

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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.signup_to_continue),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            /* ------------------- Reference ID ----------------------- */
            TextFieldWithIcons(
                "Reference ID ",
                "Enter your Reference ID",
                12,
                KeyboardType.Text,
                Icons.Filled.AccountBox,
                value = referenceID.value
            ) {
                signUpViewmodel.onReferenceTextChanged(it)
            }
            /* ------------------- Name ----------------------- */
            TextFieldWithIcons(
                "Full Name",
                "Enter your Full name",
                20,
                KeyboardType.Text,
                Icons.Filled.Person,
                value = name.value
            ) {
                signUpViewmodel.onNameTextChanged(it)
            }
            /* ------------------- Phone number ----------------------- */
            TextFieldWithIcons(
                "Phone Number",
                "Enter your Phone Number",
                10,
                KeyboardType.Phone,
                Icons.Filled.Call,
                value = phone.value
            ) {
                signUpViewmodel.onPhoneTextChanged(it)
            }
            /* ------------------- Password ----------------------- */
            PasswordTextFieldWithIcons("Password", "Create Password", value = password.value) {
                signUpViewmodel.onPasswordTextChanged(it)
            }
            /* ------------------- Confirm Password ----------------------- */
            PasswordTextFieldWithIcons("Confirm Password", "Re-Enter Password") {
                confirmPassword = it
            }
//            if (!password.equals(confirmPassword)) {
//                Text(
//                    text = "Password isn't matching",
//                    color = Color.Red,
//                    fontSize = 12.sp
//                )
//            }

            Spacer(modifier = Modifier.height(10.dp))

            /* ---------- Sign up Button -------------- */
            CustomButton("Sign up", colorResource(id = R.color.green)) {
                if (referenceID.value.length < 8) {
                    Toasty.error(context, "Please enter Reference ID", Toast.LENGTH_SHORT).show()
                } else if (name.value.length < 6) {
                    Toasty.error(context, "Please enter Full Name", Toast.LENGTH_SHORT).show()
                } else if (phone.value.length < 10) {
                    Toasty.error(context, "Please enter Phone Number", Toast.LENGTH_SHORT).show()
                } else if (password.value.length < 5) {
                    Toasty.error(context, "Please enter password", Toast.LENGTH_SHORT).show()
                } else if (confirmPassword?.length!! < 5) {
                    Toasty.error(context, "Please enter confirm password", Toast.LENGTH_SHORT).show()
                } else {
                    if (confirmPassword.equals(password.value)) {
                        navController.navigate("first_on_screen/${referenceID.value}/${name.value}/${phone.value}/${password.value}")
                    } else {
                        Toasty.error(context, "Password should be same").show()
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            CustomButton(
                text = "Already have an account? Login now",
                colorResource(id = R.color.orange)
            ) {
                navController.popBackStack()
            }

        }
    }
}