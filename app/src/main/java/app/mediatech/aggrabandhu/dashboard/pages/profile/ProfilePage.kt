package app.mediatech.aggrabandhu.dashboard.pages.profile

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.authentication.onboarding.firstOnboarding.convertDateFormatFromYYtoDD
import app.mediatech.aggrabandhu.dashboard.LogOut
import app.mediatech.aggrabandhu.di.baseUrl
import app.mediatech.aggrabandhu.utils.CircularImage
import app.mediatech.aggrabandhu.utils.EditProfileButton
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfilePage (navController: NavController?= null) {

    val context = LocalContext.current

    val profileViewModel : ProfileViewModel = hiltViewModel()
    val profileData = profileViewModel.profileData.collectAsState()
    val profileResponseCode = profileViewModel.profileResponseCode.collectAsState()


    val sharedPref = SharedPrefManager(context)
    val id = sharedPref.getMemberID()

    profileViewModel.getProfile(id, context)

    val showProgress = remember { mutableStateOf(true) }

    if (profileResponseCode.value != 0) {
        showProgress.value = false
    }

    if (showProgress.value) {
        LoadingAlertDialog()
    }

    val profileUrl = "$baseUrl${profileData.value.profileUrl}"
    val referenceID = profileData.value.reference_id
    val memberID = profileData.value.reference_id.toString()
    val name = profileData.value.name
    val fatherName = profileData.value.father_name
    val motherName = profileData.value.mother_name
    val gotra = profileData.value.gotra
    val gender = profileData.value.gender
    val phone = profileData.value.mobile_no
    val email = profileData.value.email
    val dob = profileData.value.dob
    val age = profileData.value.total_age
    val maritalStatus = profileData.value.marital_status
    val spouseName = profileData.value.spouse_name
    val marriageDate = profileData.value.marriage_date
    val marriageYears = profileData.value.marriage_age
    val profession = profileData.value.profession
    val district = profileData.value.district
    val state = profileData.value.state
    val pinCode = profileData.value.pincode
    val address = profileData.value.address
    val adharNummber = profileData.value.aadhar_no
    val adharUrl = "$baseUrl${profileData.value.aadharUrl}"
    val docNummber = profileData.value.id_no
    val docType = profileData.value.id_type
    val docImg = "$baseUrl${profileData.value.id_file}"
    val nominee = profileData.value.nominees[0].nominee
    val relation = profileData.value.nominees[0].relationship
    val nominee2 = profileData.value.nominees[0].nominee2
    val relation2 = profileData.value.nominees[0].relationship2

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
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
                .fillMaxSize()
                .blur(
                    if (sharedPref.getLoginStatus()) {
                        0.dp
                    } else {
                        30.dp
                    }
                )
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularImage(
                size = 180.dp,
                painter = rememberAsyncImagePainter(profileUrl),
            )
            Spacer(modifier = Modifier.height(10.dp))
            EditProfileButton(
                text = "Edit your Profile",
                Icons.Default.Edit,
                background = colorResource(id = R.color.orange),
                modifier = Modifier
                    .fillMaxWidth(0.36f)
            ) {
                if (sharedPref.getLoginStatus()) {
                    navController?.navigate("edit_profile_screen")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ReferralInfoCard(referenceID, onClick = {
                if (sharedPref.getLoginStatus()) {
                    shareText(context, referenceID)
                }
            }) {
                if (sharedPref.getLoginStatus()) {
                    navController?.navigate("joined_users_page")
                }
            }

            ProfileInfoCard(Icons.Default.Person, heading = "Member ID", text = memberID)
            ProfileInfoCard(Icons.Default.Person, heading = "Name", text = name)
            ProfileInfoCard(Icons.Default.Person, heading = "Father's Name", text = fatherName)
            ProfileInfoCard(Icons.Default.Person, heading = "Mother's Name", text = motherName)
            ProfileInfoCard(Icons.Default.ArtTrack, heading = "Gotra", text = gotra)
            ProfileInfoCard(Icons.Default.PhoneAndroid, heading = "Phone", text = phone)
            if (email.isNotEmpty()) {
                ProfileInfoCard(Icons.Default.Email, heading = "Email ID", text = email)
            }
            ProfileInfoCard(Icons.Default.CalendarMonth, heading = "DOB", text = if (dob.isNotEmpty()) {convertDateFormatFromYYtoDD(dob) }else {dob})
            ProfileInfoCard(Icons.Default.CalendarMonth, heading = "Age", text = age)
            ProfileInfoCard(Icons.Default.ArtTrack, heading = "Gender", text = gender)
            ProfileInfoCard(Icons.Default.FamilyRestroom, heading = "Marital Status", text = maritalStatus)
            if (maritalStatus == "Married") {
                ProfileInfoCard(Icons.Default.FamilyRestroom, heading = "Spouse Name", text = spouseName)
                ProfileInfoCard(Icons.Default.FamilyRestroom, heading = "Marriage date", text = if (dob.isNotEmpty()) {convertDateFormatFromYYtoDD(marriageDate) }else {marriageDate})
                ProfileInfoCard(Icons.Default.FamilyRestroom, heading = "Marriage Years", text = marriageYears)
            }
            ProfileInfoCard(Icons.Default.BusinessCenter, heading = "Profession", text = profession)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "Pin Code", text = pinCode)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "District", text = district)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "State", text = state)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "Address", text = address)
            DocInfoCard(
                imageUrl = adharUrl,
                heading = "Aadhar Number",
                text = adharNummber
            )
            DocInfoCard(
                imageUrl = docImg,
                heading = docType,
                text = docNummber
            )
            Text (
                text = "Nominee Details",
                modifier = Modifier.padding(vertical = 6.dp)
            )
            ProfileInfoCard(Icons.Default.Person, heading = relation, text = nominee)
            if (nominee2 != "") {
                ProfileInfoCard(Icons.Default.Person, heading = relation2, text = nominee2)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        Box(
            modifier = Modifier
                .background(if (!sharedPref.getLoginStatus()) Color.White.copy(alpha = 0.7f) else Color.Transparent)
                .fillMaxSize()
        ){}
        if (!sharedPref.getLoginStatus()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                LogOut(text = "Log in") {
                    navController?.navigate("login_screen")
                }
            }
        }
    }
}

@Composable
fun ProfileInfoCard(
    imageVector: ImageVector,
    heading : String,
    text : String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "",
                modifier = Modifier.size(34.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = heading,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun DocInfoCard(
    imageUrl: String,
    heading : String,
    text : String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "",
                modifier = Modifier
                    .widthIn(80.dp, 140.dp)
                    .heightIn(80.dp, 120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = heading,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun ReferralInfoCard (
    referralCode : String?="123ABC123",
    onClick : () -> Unit,
    onView : () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(Color.White)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "Reference ID",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                    Text(
                        text = referralCode!!,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.Black)
                    .clickable { onView() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "View Joined Users",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

fun shareText(context: Context, textToShare: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Create account with My Reference ID : $textToShare \n" +
                "Download the app now and Signups: https://play.google.com/store/apps/details?id=app.mediatech.aggrabandhu")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share your Reference ID")
    context.startActivity(shareIntent)
}

@Preview
@Composable
private fun Preview() {
    ProfilePage()
}