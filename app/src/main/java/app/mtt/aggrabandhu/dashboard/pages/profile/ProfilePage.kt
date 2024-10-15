package app.mtt.aggrabandhu.dashboard.pages.profile

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarMonth
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
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.di.baseUrl
import app.mtt.aggrabandhu.utils.CircularImage
import app.mtt.aggrabandhu.utils.CustomButton2
import app.mtt.aggrabandhu.utils.EditProfileButton
import app.mtt.aggrabandhu.utils.LoadingAlertDialog
import app.mtt.aggrabandhu.utils.SharedPrefManager
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfilePage(navController: NavController?= null) {

    val context = LocalContext.current

    val profileViewModel : ProfileViewModel = hiltViewModel()
    val profileData = profileViewModel.profileData.collectAsState()
    val profileResponseCode = profileViewModel.profileResponseCode.collectAsState()


    val sharedPref = SharedPrefManager(context)
    val id = sharedPref.getMemberID()

    profileViewModel.getProfile(id)

    val showProgress = remember { mutableStateOf(true) }

    if (profileResponseCode.value != 0) {
        showProgress.value = false
    }

    if (showProgress.value) {
        LoadingAlertDialog()
    }

    val profileUrl = "$baseUrl${profileData.value.profileUrl}"
    val referenceID = profileData.value.reference_id
    val name = profileData.value.name
    val fatherName = profileData.value.father_name
    val motherName = profileData.value.mother_name
    val gotra = profileData.value.gotra
    val phone = profileData.value.mobile_no
    val dob = profileData.value.dob
    val maritalStatus = profileData.value.marital_status
    val spouseName = profileData.value.spouse_name
    val profession = profileData.value.profession
    val district = profileData.value.district
    val state = profileData.value.state
    val pinCode = profileData.value.pincode
    val address = profileData.value.address
    val nominee = profileData.value.nominees[0].nominee
    val relation = profileData.value.nominees[0].relationship
    val nominee2 = profileData.value.nominees[0].nominee2
    val relation2 = profileData.value.nominees[0].relationship2

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
                .fillMaxSize()
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
                background = colorResource(id = R.color.orange),
                modifier = Modifier
                    .fillMaxWidth(0.36f)
            ){
                Log.d("Click", "Click - $name/$phone/${fatherName}/${motherName}/${gotra}/${maritalStatus}/${spouseName}/${dob}/${profession}")
//                navController?.navigate("edit_profile_screen/$name/$phone")
//                navController?.navigate("edit_profile_screen/$name/$phone/${fatherName}/${motherName}/${gotra}/${maritalStatus}/${spouseName}/${dob}/${profession}")
                navController?.navigate("edit_profile_screen/$name/$phone/${fatherName}/${motherName}/$pinCode/$district/$state/$address/$nominee/$relation/${nominee2}/${relation2}")
//                navController?.navigate("edit_profile_screen/$name/$phone/${fatherName}/${motherName}/${gotra}/${maritalStatus}/${spouseName}/${dob}/${profession}/$profileUrl")
            }

            Spacer(modifier = Modifier.height(20.dp))
            ReferralInfoCard(referenceID, context)
            ProfileInfoCard(Icons.Default.Person, heading = "Name", text = name)
            ProfileInfoCard(Icons.Default.Person, heading = "Father's Name", text = fatherName)
            ProfileInfoCard(Icons.Default.Person, heading = "Mother's Name", text = motherName)
            ProfileInfoCard(Icons.Default.ArtTrack, heading = "Gotra", text = gotra)
            ProfileInfoCard(Icons.Default.PhoneAndroid, heading = "Phone", text = phone)
            ProfileInfoCard(Icons.Default.CalendarMonth, heading = "DOB", text = dob)
            ProfileInfoCard(Icons.Default.FamilyRestroom, heading = "Marital Status", text = maritalStatus)
            ProfileInfoCard(Icons.Default.BusinessCenter, heading = "Profession", text = profession)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "Pin Code", text = pinCode)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "District", text = district)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "State", text = state)
            ProfileInfoCard(Icons.Default.LocationOn, heading = "Address", text = address)
            Text (
                text = "Nominee Details",
                modifier = Modifier.padding(vertical = 6.dp)
            )
            ProfileInfoCard(Icons.Default.Person, heading = relation, text = nominee)
            if (nominee2.isNotEmpty()) {
                ProfileInfoCard(Icons.Default.Person, heading = relation2, text = nominee2)
            }
            Spacer(modifier = Modifier.height(30.dp))
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

@Preview
@Composable
fun ReferralInfoCard (referralCode : String?="123ABC123", context: Context ?= null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(Color.White)
            .clickable {
                shareText(context!!, referralCode!!)
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
                    .height(25.dp)
                    .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Total users Joined : ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "9",
                    fontSize = 16.sp,
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
        putExtra(Intent.EXTRA_TEXT, textToShare)
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
