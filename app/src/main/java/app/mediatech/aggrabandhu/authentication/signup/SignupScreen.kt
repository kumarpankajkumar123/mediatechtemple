package app.mediatech.aggrabandhu.authentication.signup

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.utils.CircularImage
import app.mediatech.aggrabandhu.utils.CustomButton
import app.mediatech.aggrabandhu.utils.CustomButton3
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.PasswordTextFieldWithIcons
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons
import es.dmoral.toasty.Toasty

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

    val getOtp = signUpViewmodel.getOtp.collectAsState()
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
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(28.dp)
                        .align(Alignment.TopStart)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            // below is the composable for image.
            CircularImage(size = 250.dp, painter = painterResource(id = R.drawable.png_logo))

            Spacer(modifier = Modifier.height(10.dp))

            // Heading Login
            Text(
                text = stringResource(id = R.string.title),
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
            DocumentInstructionsScreen()
            Spacer(modifier = Modifier.height(10.dp))

            /* ------------------- Reference ID ----------------------- */
            TextFieldWithIcons(
                "Reference ID ",
                "Enter your Reference ID",
                26,
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
                40,
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
            PrivacyPolicyTxt {
                navController.navigate("privacy_policy_page")
            }
        }
    }
}

@Composable
fun DocumentInstructionsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Surface for structured layout
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 4.dp, // Shadow effect for the surface
            shape = RoundedCornerShape(8.dp), // Rounded corners for the surface
            color = Color(0xFFF5F5F5) // Light background color for contrast
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Padding inside the surface
            ) {
                // Title
                Text(
                    text = "Please make Ready these documents Before Sharing or Submitting the Form",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Instruction List
                Text(
                    text = "1. Your Mobile No. (Your account will be created using this number)",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "2. Self Clear Image for Profile Image",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "3. Latest Aadhar Copy (.JPG image with Aadhar Monogram must be printed)",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "4. Pan Card/Voter Card/Driving License/Any Other Certified Document\n   (Any One document in .JPG image format)",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PrivacyPolicyTxt(
    onPrivacyPolicyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Privacy Policy Text
        Text(
            text = buildAnnotatedString {
                append("By signing up, you agree to our ")
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Privacy Policy")
                }
            },
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { onPrivacyPolicyClick() } // Trigger privacy policy click
        )
    }
}
