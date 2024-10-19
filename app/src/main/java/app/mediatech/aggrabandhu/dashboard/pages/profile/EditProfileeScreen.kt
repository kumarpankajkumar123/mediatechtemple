package app.mediatech.aggrabandhu.dashboard.pages.profile

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons


@Composable
fun EditProfileScreen(navController: NavController?=null) {

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

//    val name = ""
//    val phone = profileViewModel.phone
//    val father = profileViewModel.father
//    val mother = profileViewModel.mother
//    val nominee = profileViewModel.nominee
//    val relation = profileViewModel.relation
//    val nominee2 = profileViewModel.nominee2
//    val relation2 = profileViewModel.relation2
//    val pinCode = profileViewModel.pinCode
//    val city = profileViewModel.city
//    val state = profileViewModel.state
//    val address = profileViewModel.address


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
                painter =
//                if (imageUri.value != null) {
//                    rememberAsyncImagePainter(model = imageUri.value)
//                } else {
                    rememberVectorPainter(image = Icons.Default.PersonPin)
//                }
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

            }
            /* ------------------- Phone number ----------------------- */
            TextFieldWithIcons(
                "Phone Number",
                "Enter your Phone Number",
                10,
                KeyboardType.Phone,
                Icons.Filled.Person,
//                phone
            ) {

            }
            /* ------------------- Reference ID ----------------------- */
            TextFieldWithIcons(
                "Father's Name",
                "Enter your Father's Name",
                12,
                KeyboardType.Text,
                Icons.Filled.Person,
//                father
            ) {

            }

            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------------- Mother Name ----------------------- */
            TextFieldWithIcons(
                "Mother's Name",
                "Enter your Mother's name",
                20,
                KeyboardType.Text,
                Icons.Filled.Person,
//                mother
            ) {

            }

            /*   ------------- Pin Code ---------------- */
            TextFieldWithIcons(
                label = "Pin Code",
                placeholder = "Area Pin Code",
                maxLength = 6,
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.PinDrop,
//                pinCode
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
//                city
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
//                state
            ) { text ->

            }
            /* ------------- Address ---------------- */
            TextFieldWithIcons(
                label = "Address",
                placeholder = "Full Address",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.LocationOn,
//                address
            ) { text ->

            }

//            TextFieldWithIcons(
//                label = "Aadhar Card Number",
//                placeholder = "Aadhar card Number",
//                maxLength = 12,
//                keyboardType = KeyboardType.Number,
//                leadingIcon = Icons.Default.Newspaper
//            ) {
//
//            }
//            Spacer(modifier = Modifier.height(10.dp))

            /*   - ------------ Nominee 1 ---------------- */
            TextFieldWithIcons(
                label = "Nominee",
                placeholder = "Nominee 1 Name",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Person2,
//                nominee
            ) { text ->
            }
            /*   ------------- Relation 1 ---------------- */
            TextFieldWithIcons(
                label = "Relation",
                placeholder = "Relation with Nominee 1",
                maxLength = 26,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.PeopleOutline,
//                relation
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
//                nominee2
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
//                relation2
            ) { text ->

            }

        }
    }
}
