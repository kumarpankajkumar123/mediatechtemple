package app.mtt.aggrabandhu.authentication.signup

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import app.mtt.aggrabandhu.utils.CustomButton3
import app.mtt.aggrabandhu.utils.LoadingAlertDialog
import app.mtt.aggrabandhu.utils.PasswordTextFieldWithIcons
import app.mtt.aggrabandhu.utils.SharedPrefManager
import app.mtt.aggrabandhu.utils.TextFieldWithIcons
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.sign

@Composable
fun SignupScreen(navController: NavController) {
    val context = LocalContext.current

    val signUpViewmodel : SignUpViewmodel = hiltViewModel()

    val sharedPrefManager = SharedPrefManager(context)
    signUpViewmodel.initSharedPrefs(sharedPrefManager)

    val showProgressDialog = remember { mutableStateOf(false) }

    if (showProgressDialog.value) {
        LoadingAlertDialog()  // Show progress dialog
    }

    val getOtp = (signUpViewmodel.getOtp.collectAsState())
    val verifyOtp = (signUpViewmodel.verifyOtp.collectAsState())
    val referenceCode = (signUpViewmodel.checkReference.collectAsState())

    if (referenceCode.value != 0) {
        if (!signUpViewmodel.isNext) {
            if (referenceCode.value == 200) {
                showProgressDialog.value = false
                signUpViewmodel.isNext = true
                navController.navigate("first_on_screen/${signUpViewmodel.referenceIDSP}/${signUpViewmodel.fullNameSP}/${signUpViewmodel.phoneSP}/${signUpViewmodel.passwordSP}")
            } else {
                showProgressDialog.value = false
            }
        }
    }

    if (verifyOtp.value == "verified"){
        signUpViewmodel.isOtpVerified = true
        showProgressDialog.value = false
    } else {
        showProgressDialog.value = false
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
                value = signUpViewmodel.referenceIDSP
            ) {
                signUpViewmodel.referenceIDSP = it
                sharedPrefManager.saveReferenceID(it)
            }
            /* ------------------- Name ----------------------- */
            TextFieldWithIcons(
                "Full Name",
                "Enter your Full name",
                20,
                KeyboardType.Text,
                Icons.Filled.Person,
                value = signUpViewmodel.fullNameSP
            ) {
                signUpViewmodel.fullNameSP = it
                sharedPrefManager.saveFullName(it)
            }
            /* ------------------- Phone number ----------------------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextFieldWithIcons(
                    "Phone Number",
                    "Phone Number",
                    10,
                    KeyboardType.Phone,
                    Icons.Filled.Call,
                    value = signUpViewmodel.phoneSP,
                    modifier = Modifier.fillMaxWidth(0.65f)
                ) {
                    signUpViewmodel.phoneSP = it
                    sharedPrefManager.savePhone(it)
                }
                Spacer(modifier = Modifier.width(8.dp))
                CustomButton3(
                    text = "Send OTP",
                    background = colorResource(id = R.color.orange)
                ) {
                    if (signUpViewmodel.phoneSP.length == 10) {
                        showProgressDialog.value = true
                        signUpViewmodel.getOtp(signUpViewmodel.phoneSP, context)
                        Log.d("Phone", signUpViewmodel.phoneSP)
                    } else {
                        Toasty.error(context, "Please Enter valid phone number", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (getOtp.value == "Otp sended") {
                showProgressDialog.value = false
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextFieldWithIcons(
                        "OTP",
                        "Enter your OTP",
                        6,
                        KeyboardType.Phone,
                        Icons.Filled.Textsms,
                        value = signUpViewmodel.otpTxt,
                        modifier = Modifier.fillMaxWidth(0.65f)
                    ) {
                        signUpViewmodel.otpTxt = (it)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    CustomButton3(
                        text = "Verify OTP",
                        background = colorResource(id = R.color.orange)
                    ) {
                        if (signUpViewmodel.otpTxt.length == 6) {
                            showProgressDialog.value = true
                            signUpViewmodel.verifyOtp(signUpViewmodel.phoneSP, signUpViewmodel.otpTxt, context)
                        } else {
                            Toasty.error(context, "Please Enter 6 digit OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else if(getOtp.value == "Member Already Exist in this number"){
                showProgressDialog.value = false
            } else {
                showProgressDialog.value = false
//                Toasty.error(context, "Else", Toast.LENGTH_SHORT).show()
            }
            /* ------------------- Password ----------------------- */
            PasswordTextFieldWithIcons("Password", "Create Password", value = signUpViewmodel.passwordSP) {
                sharedPrefManager.savePassword(it)
                signUpViewmodel.passwordSP = it
            }
            /* ------------------- Confirm Password ----------------------- */
            PasswordTextFieldWithIcons("Confirm Password", "Re-Enter Password") {
                signUpViewmodel.confirmPassword = it
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
            CustomButton("Next", colorResource(id = R.color.green)) {
                if (signUpViewmodel.referenceIDSP.length < 8) {
                    Toasty.error(context, "Please enter Reference ID", Toast.LENGTH_SHORT).show()
                } else if (signUpViewmodel.fullNameSP.length < 6) {
                    Toasty.error(context, "Please enter Full Name", Toast.LENGTH_SHORT).show()
                } else if (signUpViewmodel.phoneSP.length < 10) {
                    Toasty.error(context, "Please enter Phone Number", Toast.LENGTH_SHORT).show()
                } else if (!signUpViewmodel.isOtpVerified) {
                    Toasty.error(context, "Please Verify Your phone", Toast.LENGTH_SHORT).show()
                } else if (signUpViewmodel.passwordSP.length < 5) {
                    Toasty.error(context, "Please enter password", Toast.LENGTH_SHORT).show()
                } else if (signUpViewmodel.confirmPassword.length < 5) {
                    Toasty.error(context, "Please enter confirm password", Toast.LENGTH_SHORT).show()
                } else {
                    if (signUpViewmodel.confirmPassword == signUpViewmodel.passwordSP) {
                        signUpViewmodel.isNext = false
                        showProgressDialog.value = true
                        signUpViewmodel.checkReferenceCode(signUpViewmodel.referenceIDSP, context)
//                    Toasty.success(context, signUpViewmodel.referenceIDSP, Toast.LENGTH_SHORT).show()
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