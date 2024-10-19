package app.mediatech.aggrabandhu.authentication.onboarding.firstOnboarding

import android.net.Uri
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
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.PermContactCalendar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.compressImageToUri
import app.mediatech.aggrabandhu.utils.CustomButton
import app.mediatech.aggrabandhu.utils.DatePickerField
import app.mediatech.aggrabandhu.utils.DropDownField
import app.mediatech.aggrabandhu.utils.EditProfileButton
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons
import coil.compose.rememberAsyncImagePainter
import es.dmoral.toasty.Toasty
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun FirstOnboardingScreen(navController: NavController?=null) {

    val context = LocalContext.current

    val onboarding1Viewmodel : Onboarding1Viewmodel = hiltViewModel()

    val sharedPref = SharedPrefManager(context)
    onboarding1Viewmodel.initSharedPrefs(sharedPref)

    /* --------------------- Get Gender Data -------------------*/
    val genderList = onboarding1Viewmodel.genderList
    /* ----------------------- ------------- -----------------------*/

    /* --------------------- Get Gotra Data -------------------*/
    val gotra = onboarding1Viewmodel.gotra.collectAsState()
    /* ----------------------- ------------- -----------------------*/

    /* --------------------- Get Profession Data -------------------*/
    val profession = onboarding1Viewmodel.profession.collectAsState()
    /* ----------------------- ------------- -----------------------*/

    val imageUri by onboarding1Viewmodel.imageUri.collectAsState()
    val dob = onboarding1Viewmodel.dobTextFieldState.collectAsState()
    val marriageDate = onboarding1Viewmodel.marriageDateTextFieldState.collectAsState()
    val selectedGotra = onboarding1Viewmodel.selectedGotra.collectAsState()
    val selectedGender = onboarding1Viewmodel.selectedGender.collectAsState()
    val selectedMaritalStatus = onboarding1Viewmodel.selectedMaritalStatus.collectAsState()
    val spouseName = onboarding1Viewmodel.spouseNameText.collectAsState()
    val selectedProfession = onboarding1Viewmodel.professionState.collectAsState()

    val referenceID : String = onboarding1Viewmodel.referenceID
    val name : String = onboarding1Viewmodel.getName
    val phone : String = onboarding1Viewmodel.phone
    val password : String = onboarding1Viewmodel.password

//    Toasty.success(context,"$referenceID, $name, $phone, $password").show()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val compressed = compressImageToUri(uri, context)
                onboarding1Viewmodel.onImageUriChanged(compressed!!)
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
            contentDescription = null,
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

            Spacer(modifier = Modifier.height(20.dp))

            // below is the composable for image.
//            CircularImage(size = 180.dp, painter = painterResource(id = R.drawable.png_logo))

            if (profession.value.isEmpty()) {
                LoadingAlertDialog()
            }

            Image(
                modifier = Modifier
                    .clickable { }
                    .clip(CircleShape)
                    .size(180.dp)
                    .border(BorderStroke(2.dp, Color.Black), CircleShape),
                painter = if (imageUri != null) {
                    rememberAsyncImagePainter(model = imageUri)
                } else {
                    rememberVectorPainter(image = Icons.Default.PersonPin)
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

            // Heading Login
            Text(
                text = "Welcome $name",
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
                "Father's / Husband's Name",
                "Enter your Father's Name",
                40,
                KeyboardType.Text,
                Icons.Filled.Person,
                onboarding1Viewmodel.fatherNameSP
            ) {
                sharedPref.saveFatherName(it)
                onboarding1Viewmodel.fatherNameSP = it
            }

            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------------- Name ----------------------- */
            TextFieldWithIcons(
                "Mother's Name",
                "Enter your Mother's name",
                40,
                KeyboardType.Text,
                Icons.Filled.Person,
                onboarding1Viewmodel.motherNameSP
            ) {
                sharedPref.saveMotherName(it)
                onboarding1Viewmodel.motherNameSP = it
            }

            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------- Select Gotra ------------ */
            DropDownField(
                selectedValue = selectedGotra.value,
                options = gotra.value.map { it.name },
                label = "Gotra",
                Icons.Default.ArtTrack,
                onValueChangedEvent = {
                    onboarding1Viewmodel.onSelectedGotraTextChanged(it)
                }
            )
            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------- Select Gotra ------------ */
            DropDownField(
                selectedValue = selectedGender.value,
                options = genderList,
                label = "Gender",
                Icons.Default.Transgender,
                onValueChangedEvent = {
                    onboarding1Viewmodel.selectedGenderChanged(it)
                }
            )
            /* ------------- Select Gotra ------------ */
            DropDownField(
                selectedValue = selectedMaritalStatus.value,
                options = onboarding1Viewmodel.maritalStatusList,
                label = "Marital Status",
                Icons.Default.FamilyRestroom,
                onValueChangedEvent = {
                    onboarding1Viewmodel.selectedMaritalStatusChanged(it)
                }
            )
            if(selectedMaritalStatus.value == "Married"){
                // Spacer(modifier = Modifier.height(10.dp))
                /* ------------------- Name ----------------------- */
                TextFieldWithIcons(
                    "Spouse Name",
                    "Enter your Spouse name",
                    20,
                    KeyboardType.Text,
                    Icons.Filled.Person,
                    spouseName.value
                ) {
                    onboarding1Viewmodel.spouseNameChanged(it)
                }
                /* ------------- Select Date ------------ */
                DatePickerField(
                    label = "Date of Marriage",
                    value = marriageDate.value,
                    onClick = { date ->
                        if (date.isNotEmpty()) {
                            onboarding1Viewmodel.onMarriageDateTextChanged(date)
                            onboarding1Viewmodel.marriageYears = calculateAge(date)
                            Log.d("marriageDate", onboarding1Viewmodel.marriageYears.toString())
                            Toasty.success(context, onboarding1Viewmodel.marriageYears.toString(), Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Log.d("marriageDate", "Empty marriageDate")
                        }
                    }
                )
            }
            if (marriageDate.value.isNotEmpty() && marriageDate.value != "No") {
                if (calculateAge(marriageDate.value) != 0) {
                    Log.d("marriageDate", marriageDate.value)
                    DropDownField(
                        selectedValue = onboarding1Viewmodel.marriageYears.toString(),
                        options = emptyList(),
                        label = "Current Marriage Years",
                        Icons.Default.PermContactCalendar,
                        onValueChangedEvent = {

                        }
                    )
                }
            }
            /* ------------- Select Date ------------ */
            DatePickerField(
                label = "Date of Birth",
                value = dob.value,
                onClick = { date ->
                    if (date.isNotEmpty()) {
                        if (calculateAge(date) in 18..70) {
                            onboarding1Viewmodel.onDobTextChanged(date)
                            onboarding1Viewmodel.ageYears = calculateAge(date)
                            Toasty.success(
                                context,
                                onboarding1Viewmodel.ageYears.toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Log.d("Age", onboarding1Viewmodel.ageYears.toString())
                        } else {
                            Toasty.error(context, "People of Age group of 18-70 are allowed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("Date", "Empty Date")
                    }
                }
            )
            /* ------------- Selected Date ------------ */

            if (dob.value.isNotEmpty()) {
                if (calculateAge(dob.value) != 0) {
                    Log.d("AgeField", dob.value)
                    DropDownField(
                        selectedValue = onboarding1Viewmodel.ageYears.toString(),
                        options = emptyList(),
                        label = "Current Age",
                        Icons.Default.PermContactCalendar,
                        onValueChangedEvent = {

                        }
                    )
                }
            }

            /* ------------- Select Profession ------------ */
            DropDownField(
                selectedValue = selectedProfession.value,
                options = profession.value.map { it.name },
                label = "Profession  of Donor",
                Icons.Default.BusinessCenter,
                onValueChangedEvent = {
                    onboarding1Viewmodel.onProfessionChanged(it)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(text = "Next", background = colorResource(id = R.color.orange)) {
                if (imageUri == null) {
                    Toasty.error(context, "Please Select Image", Toast.LENGTH_SHORT).show()
                } else if (onboarding1Viewmodel.fatherNameSP.length <= 3) {
                    Toasty.error(context, "Please Enter Father Name", Toast.LENGTH_SHORT).show()
                } else if (onboarding1Viewmodel.motherNameSP.length <= 3) {
                    Toasty.error(context, "Please Enter Mother Name", Toast.LENGTH_SHORT).show()
                } else if (selectedGotra.value == "") {
                    Toasty.error(context, "Please Select Gotra", Toast.LENGTH_SHORT).show()
                } else if (selectedGender.value == "") {
                    Toasty.error(context, "Please Select Gender", Toast.LENGTH_SHORT).show()
                } else if (selectedMaritalStatus.value == "") {
                    Toasty.error(context, "Please Select Marital Status", Toast.LENGTH_SHORT).show()
                } else if (dob.value == "") {
                    Toasty.error(context, "Please Select DOB", Toast.LENGTH_SHORT).show()
                } else if (onboarding1Viewmodel.ageYears <18 || onboarding1Viewmodel.ageYears > 70) {
                    Toasty.error(context, "Only people of age group of 18 - 70 are allowed", Toast.LENGTH_SHORT).show()
                } else if (selectedProfession.value == "") {
                    Toasty.error(context, "Please Select Profession", Toast.LENGTH_SHORT).show()
                } else if (selectedMaritalStatus.value == "Married" && (spouseName.value == "" || marriageDate.value == "")) {
                    Toasty.error(context, "Please Fill all the fields", Toast.LENGTH_SHORT).show()
                } else {
                    onboarding1Viewmodel.spouseNameChanged("No")
                    onboarding1Viewmodel.onMarriageDateTextChanged("No")
                        val sp = SharedPrefManager(context)

                        val enCodedUri1 = imageUri.toString()
                        val enCodedUri = (Uri.encode(imageUri.toString()))

                        sp.saveProfileImageUri(enCodedUri1)
                        sp.saveFatherName(onboarding1Viewmodel.fatherNameSP)
                        sp.saveMotherName(onboarding1Viewmodel.motherNameSP)
                        sp.saveGotra(selectedGotra.value)
                        sp.saveMarital(selectedMaritalStatus.value)
                        sp.saveSpouseName(spouseName.value)
                        sp.saveDOB(dob.value)
                        sp.saveMarriageDate(marriageDate.value)
                        sp.saveProfession(selectedProfession.value)

                        Log.d(
                            "Marriage",
                            "${onboarding1Viewmodel.marriageYears}, ${onboarding1Viewmodel.ageYears}"
                        )
                        navController?.navigate("second_on_screen/$referenceID/$name/$phone/$password/${onboarding1Viewmodel.fatherNameSP}/${onboarding1Viewmodel.motherNameSP}/${selectedGender.value}/${selectedMaritalStatus.value}/${spouseName.value}/${marriageDate.value}/${onboarding1Viewmodel.marriageYears}/${dob.value}/${onboarding1Viewmodel.ageYears}/$enCodedUri")
                    }
                }
            }
        }
    }

fun calculateAge(dob: String?="1940-08-23", dateFormat: String = "yyyy-MM-dd"): Int {
    // Parse the string into a LocalDate using the provided date format
    val formatter = DateTimeFormatter.ofPattern(dateFormat)
    val parsedDob = LocalDate.parse(dob, formatter)

    // Get the current date
    val today = LocalDate.now()

    // Calculate the period between the date of birth and today
    return Period.between(parsedDob, today).years
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    FirstOnboardingScreen(navController = null)
}