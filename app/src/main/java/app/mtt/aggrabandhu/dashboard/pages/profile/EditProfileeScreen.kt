package app.mtt.aggrabandhu.dashboard.pages.profile

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
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.PersonPin
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
import app.mtt.aggrabandhu.authentication.onboarding.firstOnboarding.calculateAge
import app.mtt.aggrabandhu.utils.CustomButton
import app.mtt.aggrabandhu.utils.DatePickerField
import app.mtt.aggrabandhu.utils.DropDownField
import app.mtt.aggrabandhu.utils.LoadingAlertDialog
import app.mtt.aggrabandhu.utils.SelectImageCardWithButton
import app.mtt.aggrabandhu.utils.SharedPrefManager
import app.mtt.aggrabandhu.utils.TextFieldWithIcons
import app.mtt.aggrabandhu.utils.prepareFilePart
import app.mtt.aggrabandhu.viewmodel.Onboarding1Viewmodel
import coil.compose.rememberAsyncImagePainter
import es.dmoral.toasty.Toasty


@Composable
fun EditProfileScreen(navController: NavController?=null) {

    val context = LocalContext.current

    val profileViewModel : EditProfileViewModel = hiltViewModel()

    val showProgress = remember { mutableStateOf(true) }

//    if (editProfileResponseCode.value != 0) {
//        showProgress.value = false
//    }

    if (showProgress.value) {
        LoadingAlertDialog()
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {

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
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp)
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
                painter = //if (imageUri.value != null) {
//                    rememberAsyncImagePainter(model = imageUri.value)
//                } else {
                    rememberVectorPainter(image = Icons.Default.PersonPin)
//          }
            ,
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

            /* ------------------- Name ID ----------------------- */
            TextFieldWithIcons(
                "Full Name",
                "Enter your Full Name",
                12,
                KeyboardType.Text,
                Icons.Filled.Person,
//                name
            ) {
                profileViewModel.onNameTextChanged(it)
            }
            /* ------------------- Phone number ----------------------- */
            TextFieldWithIcons(
                "Phone Number",
                "Enter your Phone Number",
                10,
                KeyboardType.Phone,
                Icons.Filled.Person,
//                phone.value
            ) {
                profileViewModel.onPhoneTextChanged(it)
            }
            /* ------------------- Reference ID ----------------------- */
            TextFieldWithIcons(
                "Father's Name",
                "Enter your Father's Name",
                12,
                KeyboardType.Text,
                Icons.Filled.Person,
//                fatherName.value
            ) {
                profileViewModel.onFatherNameTextChanged(it)
            }

            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------------- Mother Name ----------------------- */
            TextFieldWithIcons(
                "Mother's Name",
                "Enter your Mother's name",
                20,
                KeyboardType.Text,
                Icons.Filled.Person,
//                motherName.value
            ) {
                profileViewModel.onMotherNameTextChanged(it)
            }

            /*   ------------- Pin Code ---------------- */
            TextFieldWithIcons(
                label = "Pin Code",
                placeholder = "Area Pin Code",
                maxLength = 6,
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.PinDrop,
            ) { text ->

            }
            // Spacer(modifier = Modifier.height(15.dp))
            /*   - ------------ City ---------------- */
            TextFieldWithIcons(
                label = "City",
                placeholder = "City",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.LocationCity,
            ) { text ->

            }
            // Spacer(modifier = Modifier.height(15.dp))
            /*   - ------------ State ---------------- */
            TextFieldWithIcons(
                label = "State",
                placeholder = "State",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.LocationOn,
            ) { text ->

            }
            /* ------------- Address ---------------- */
            TextFieldWithIcons(
                label = "Address",
                placeholder = "Full Address",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.LocationOn,
            ) { text ->

            }

            TextFieldWithIcons(
                label = "Aadhar Card Number",
                placeholder = "Aadhar card Number",
                maxLength = 12,
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.Newspaper
            ) {

            }
            Spacer(modifier = Modifier.height(10.dp))

            /*   - ------------ Nominee 1 ---------------- */
            TextFieldWithIcons(
                label = "Nominee",
                placeholder = "Nominee 1 Name",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Person2,
            ) { text ->
            }
            /*   ------------- Relation 1 ---------------- */
            TextFieldWithIcons(
                label = "Relation",
                placeholder = "Relation with Nominee 1",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.PeopleOutline,
            ) { text ->

            }
            // Spacer(modifier = Modifier.height(15.dp))
            /*   - ------------ Nominee 2 ---------------- */
            TextFieldWithIcons(
                label = "Nominee 2",
                placeholder = "Nominee 2 Name",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Person2,
            ) { text ->

            }
            // Spacer(modifier = Modifier.height(15.dp))
            /*   - ------------ Relation ---------------- */
            TextFieldWithIcons(
                label = "Relation",
                placeholder = "Relation with Nominee 2",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.PeopleOutline,
            ) { text ->

            }

        }
    }
}
