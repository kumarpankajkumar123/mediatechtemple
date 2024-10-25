package app.mediatech.aggrabandhu.dashboard.pages.profile.editProfile

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.PermContactCalendar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.authentication.onboarding.firstOnboarding.calculateAge
import app.mediatech.aggrabandhu.dashboard.pages.profile.ProfileViewModel
import app.mediatech.aggrabandhu.utils.CustomButton
import app.mediatech.aggrabandhu.utils.DatePickerField
import app.mediatech.aggrabandhu.utils.DropDownField
import app.mediatech.aggrabandhu.utils.EditProfileButton
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons
import coil.compose.rememberAsyncImagePainter
import es.dmoral.toasty.Toasty


@Composable
fun EditProfileScreen(navController: NavController?=null) {

    val context = LocalContext.current

    val profileViewModel : ProfileViewModel = hiltViewModel()
    val profileResponseCode = profileViewModel.profileResponseCode.collectAsState()

    val editProfileResponseCode = profileViewModel.editProfileResponseCode.collectAsState()

    val postalData = profileViewModel.tahsilList.collectAsState()

    val sharedPref = SharedPrefManager(context)
    val id = sharedPref.getMemberID()

    profileViewModel.getProfile(id, context)

    val showProgress = remember { mutableStateOf(true) }

    if (postalData.value.Status == "Success"){
        profileViewModel.district = (postalData.value.PostOffice[0].District)
        profileViewModel.state = (postalData.value.PostOffice[0].State)
    } else if (postalData.value.Status == "Error"){
        Toasty.error(context, "No records found", Toast.LENGTH_SHORT).show()
    }

    if (editProfileResponseCode.value != 0) {
        if (editProfileResponseCode.value == 200) {
            if (profileViewModel.isProfileEdited) {
                showProgress.value = false
                profileViewModel.isProfileEdited = false
                navController?.navigate("dashboard_screen") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
            } else {
            showProgress.value = false
        }
    }

    if (profileResponseCode.value != 0) {
        if (profileViewModel.isProfile) {
            showProgress.value = false
            profileViewModel.getProfile()
            profileViewModel.isProfile = false
        }
    }

    if (showProgress.value) {
        LoadingAlertDialog()
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                profileViewModel.imageUri = it
            }
        }
    )

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.textured_bg), // Replace with your image resource
            contentDescription = "back",
            contentScale = ContentScale.Crop, // Adjust content scaling as needed
            modifier = Modifier.fillMaxSize(), // Ensure the image fills the entire screen
            alpha = 0.3f
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable { navController?.popBackStack() }
                        .size(28.dp)
                )
                Text(
                    text = "Edit your Profile",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                modifier = Modifier
                    .clickable { }
                    .clip(CircleShape)
                    .size(180.dp)
                    .border(BorderStroke(2.dp, Color.Black), CircleShape),
                painter = if (profileViewModel.imageUri != null) {
                        rememberAsyncImagePainter(model = profileViewModel.imageUri)
                    } else {
                        rememberAsyncImagePainter(model = profileViewModel.profileUrl)
                    },
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            EditProfileButton(
                text = "Upload your Profile",
                Icons.Default.PhotoCamera,
                background = colorResource(id = R.color.orange),
                modifier = Modifier
                    .fillMaxWidth(0.43f)
            ){
                galleryLauncher.launch("image/*")
            }

            Spacer(modifier = Modifier.height(10.dp))

            /* ------------------- Name ID ----------------------- */
            TextFieldWithIcons(
                "Full Name",
                "Enter your Full Name",
                40,
                KeyboardType.Text,
                Icons.Filled.Person,
                profileViewModel.name
            ) {
                profileViewModel.name = it
            }

            /* ------------------- Reference ID ----------------------- */
            TextFieldWithIcons(
                "Father's Name",
                "Enter your Father's Name",
                40,
                KeyboardType.Text,
                Icons.Filled.Person,
                profileViewModel.father
            ) {
                profileViewModel.father = it
            }

            /* ------------------- Mother Name ----------------------- */
            TextFieldWithIcons(
                "Mother's Name",
                "Enter your Mother's name",
                40,
                KeyboardType.Text,
                Icons.Filled.Person,
                profileViewModel.motherName
            ) {
                profileViewModel.motherName = it
            }

            TextFieldWithIcons(
                label = "Email (OPTIONAL)",
                placeholder = "Email (OPTIONAL)",
                maxLength = 26,
                keyboardType = KeyboardType.Email,
                isRequired = false,
                leadingIcon = Icons.Default.LocationOn,
                value = profileViewModel.email
            ) { text ->
                profileViewModel.email = text
            }

            /* ------------- Select Marital Status ------------ */
            DropDownField(
                selectedValue = profileViewModel.maritalStatus,
                options = profileViewModel.maritalStatusList,
                label = "Marital Status",
                Icons.Default.FamilyRestroom,
                onValueChangedEvent = {
                    profileViewModel.maritalStatus = it
                }
            )

            if(profileViewModel.maritalStatus == "Married"){
                // Spacer(modifier = Modifier.height(10.dp))
                /* ------------------- Name ----------------------- */
                TextFieldWithIcons(
                    "Spouse Name",
                    "Enter your Spouse name",
                    40,
                    KeyboardType.Text,
                    Icons.Filled.Person,
                    profileViewModel.spouseName
                ) {
                    profileViewModel.spouseName = (it)
                }
                /* ------------- Select Date ------------ */
                DatePickerField(
                    label = "Date of Marriage",
                    value = profileViewModel.marriageDate,
                    onClick = { date ->
                        if (date.isNotEmpty()) {
                            profileViewModel.marriageDate = (date)
                            profileViewModel.marriageYears = calculateAge(date).toString()
                            Log.d("marriageDate", profileViewModel.marriageYears)
                            Toasty.success(context, profileViewModel.marriageYears, Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("marriageDate", "Empty marriageDate")
                        }
                    }
                )
            } else {
                profileViewModel.spouseName = ""
                profileViewModel.marriageDate = ""
                profileViewModel.marriageYears = ""
            }
            if (profileViewModel.marriageDate.isNotEmpty() && profileViewModel.marriageDate != "No") {
                if (calculateAge(profileViewModel.marriageDate) != 0) {
                    Log.d("marriageDate", profileViewModel.marriageDate)
                    DropDownField(
                        selectedValue = profileViewModel.marriageYears,
                        options = emptyList(),
                        label = "Current Marriage Years",
                        Icons.Default.PermContactCalendar,
                        onValueChangedEvent = {

                        }
                    )
                }
            }

            /*   ------------- Pin Code ---------------- */
            TextFieldWithIcons(
                label = "Pin Code",
                placeholder = "Area Pin Code",
                maxLength = 6,
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.PinDrop,
                profileViewModel.pinCode
            ) {
                profileViewModel.pinCode = it
                if (it.length == 6) {
                    profileViewModel.getPostalData(it)
                }
            }

            DropDownField(
                selectedValue = profileViewModel.state,
                options = emptyList(),
                label = "State",
                Icons.Default.LocationOn,
                onValueChangedEvent = {
                }
            )

            DropDownField(
                selectedValue = profileViewModel.district,
                options = emptyList(),
                label = "City",
                Icons.Default.LocationCity,
                onValueChangedEvent = {
                }
            )
            DropDownField (
                selectedValue = profileViewModel.tahsil,
                options = postalData.value.PostOffice.map { it.Name },
                label = "Tahsil",
                imageVector = Icons.Default.LocationCity,
                onValueChangedEvent = {
//                    profileViewModel.selectedTahsilChanged(it)
                    profileViewModel.tahsil = it
                    Log.d("City", "${postalData.value.PostOffice[0].District}")
                }
            )
            /* ------------- Address ---------------- */
            TextFieldWithIcons(
                label = "Address",
                placeholder = "Full Address",
                maxLength = 50,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.LocationOn,
                profileViewModel.address
            ) {
                profileViewModel.address = it
            }

            /*   - ------------ Nominee 1 ---------------- */
            TextFieldWithIcons(
                label = "Nominee",
                placeholder = "Nominee 1 Name",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Person2,
                profileViewModel.nominee
            ) { profileViewModel.nominee = it }
            /*   ------------- Relation 1 ---------------- */
            TextFieldWithIcons(
                label = "Relation",
                placeholder = "Relation with Nominee 1",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.PeopleOutline,
                profileViewModel.relation
            ) {
                profileViewModel.relation = it
            }
            /*   - ------------ Nominee 2 ---------------- */
            TextFieldWithIcons(
                label = "Nominee 2",
                placeholder = "Nominee 2 Name",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Person2,
                profileViewModel.nominee2
            ) {
                profileViewModel.nominee2 = it
            }
            /*   - ------------ Relation ---------------- */
            TextFieldWithIcons(
                label = "Relation",
                placeholder = "Relation with Nominee 2",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.PeopleOutline,
                profileViewModel.relation2
            ) {
                profileViewModel.relation2 = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(text = "SAVE", background = colorResource(id = R.color.orange)) {
                showProgress.value = true
                profileViewModel.editProfile(context)
            }
        }

    }
}
