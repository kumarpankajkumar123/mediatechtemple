package app.mtt.aggrabandhu.authentication.onboarding.firstOnboarding

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.utils.CustomButton
import app.mtt.aggrabandhu.utils.DatePickerField
import app.mtt.aggrabandhu.utils.DropDownField
import app.mtt.aggrabandhu.utils.LoadingAlertDialog
import app.mtt.aggrabandhu.utils.SharedPrefManager
import app.mtt.aggrabandhu.utils.TextFieldWithIcons
import app.mtt.aggrabandhu.viewmodel.Onboarding1Viewmodel
import coil.compose.rememberAsyncImagePainter
import es.dmoral.toasty.Toasty
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun FirstOnboardingScreen(navController: NavController?=null) {

    val context = LocalContext.current

    val onboarding1Viewmodel : Onboarding1Viewmodel = hiltViewModel()
    onboarding1Viewmodel.initSharedPrefs(context)

    /* --------------------- Get Gotra Data -------------------*/
    val gotra = onboarding1Viewmodel.gotra.collectAsState()
    /* ----------------------- ------------- -----------------------*/

    /* --------------------- Get Profession Data -------------------*/
    val profession = onboarding1Viewmodel.profession.collectAsState()
    /* ----------------------- ------------- -----------------------*/

    val imageUri = onboarding1Viewmodel.imageUri.collectAsState()
    val father = onboarding1Viewmodel.fatherNameFieldState.collectAsState()
    val mother = onboarding1Viewmodel.motherNameFieldState.collectAsState()
    val dob = onboarding1Viewmodel.dobTextFieldState.collectAsState()
    val selectedGotra = onboarding1Viewmodel.selectedGotra.collectAsState()
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
                onboarding1Viewmodel.onImageUriChanged(uri)
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
                painter = if (imageUri.value != null) {
                    rememberAsyncImagePainter(model = imageUri.value)
                } else {
                    rememberVectorPainter(image = Icons.Default.PersonPin)
                },
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Icon (
                modifier = Modifier
                    .padding(4.dp)
                    .size(30.dp)
                    .clickable { galleryLauncher.launch("image/*") },
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "",
                tint = colorResource(id = R.color.orange)
            )

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
                "Father's Name",
                "Enter your Father's Name",
                12,
                KeyboardType.Text,
                Icons.Filled.Person,
                father.value
            ) {
                onboarding1Viewmodel.onFatherNameTextChanged(it)
            }

            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------------- Name ----------------------- */
            TextFieldWithIcons(
                "Mother's Name",
                "Enter your Mother's name",
                20,
                KeyboardType.Text,
                Icons.Filled.Person,
                mother.value
            ) {
                onboarding1Viewmodel.onMotherNameTextChanged(it)
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
            }
            /* ------------- Select Date ------------ */
            DatePickerField(
                label = "Date of Birth",
                value = dob.value,
                onClick = { date ->
                    if (date.isNotEmpty()) {
                        onboarding1Viewmodel.onDobTextChanged(date)
                        Log.d("Age", calculateAge(date).toString())
                        Toasty.success(context, calculateAge(date).toString(), Toast.LENGTH_SHORT)
                            .show()
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
                        selectedValue = calculateAge(dob.value).toString(),
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
                label = "Profession",
                Icons.Default.BusinessCenter,
                onValueChangedEvent = {
                    onboarding1Viewmodel.onProfessionChanged(it)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(text = "Next", background = colorResource(id = R.color.orange)) {
                val enCodedUri1 = imageUri.value.toString()
                val enCodedUri = (Uri.encode(imageUri.toString()))
                val sp = SharedPrefManager(context)
                sp.saveProfileImageUri(enCodedUri1)
                sp.saveFatherName(father.value)
                sp.saveMotherName(mother.value)
                sp.saveGotra(selectedGotra.value)
                sp.saveMarital(selectedMaritalStatus.value)
                sp.saveSpouseName(spouseName.value)
                sp.saveDOB(dob.value)
                sp.saveProfession(selectedProfession.value)

                navController?.navigate("second_on_screen/$referenceID/$name/$phone/$password/${father.value}/${mother.value}/${selectedGotra.value}/${selectedMaritalStatus.value}/${spouseName.value}/${dob.value}/${selectedProfession.value}/$enCodedUri")
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