package app.mtt.aggrabandhu.authentication.onboarding

import android.net.Uri
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
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.utils.CustomButton
import app.mtt.aggrabandhu.utils.DatePickerField
import app.mtt.aggrabandhu.utils.DropDownField
import app.mtt.aggrabandhu.utils.TextFieldWithIcons
import coil.compose.rememberAsyncImagePainter

@Composable
fun FirstOnboardingScreen(navController: NavController?=null) {
    val context = LocalContext.current
    var father: String? = ""
    var mother: String? = ""

    val name = "Suresh"

    val optionsList = arrayListOf(
        "SD","BD","A","AD"
    )
    val maritalStatusList = arrayListOf(
        "Married","Unmarried"
    )

    val selectedGotra = remember { mutableStateOf("") }
    val selectedMaritalStatus = remember { mutableStateOf("") }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
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

            Image(
                modifier = Modifier
                    .clickable { }
                    .clip(CircleShape)
                    .size(180.dp)
                    .border(BorderStroke(2.dp, Color.Black), CircleShape),
                painter = if (imageUri != null) {
                    rememberAsyncImagePainter(model = imageUri!!)
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
                Icons.Filled.Person
            ) {
                father = it
            }

            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------------- Name ----------------------- */
            TextFieldWithIcons(
                "Mother's Name",
                "Enter your Mother's name",
                20,
                KeyboardType.Text,
                Icons.Filled.Person
            ) {
                mother = it
            }

            // Spacer(modifier = Modifier.height(10.dp))
            /* ------------- Select Gotra ------------ */
            DropDownField(
                selectedValue = selectedGotra.value,
                options = optionsList,
                label = "Gotra",
                Icons.Default.ArtTrack,
                onValueChangedEvent = {
                    selectedGotra.value = it
                }
            )
            /* ------------- Select Gotra ------------ */
            DropDownField(
                selectedValue = selectedMaritalStatus.value,
                options = maritalStatusList,
                label = "Marital Status",
                Icons.Default.FamilyRestroom,
                onValueChangedEvent = {
                    selectedMaritalStatus.value = it
                }
            )

            /* ------------- Select Date ------------ */
            DatePickerField(
                label = "Date of Birth",
                onClick = { date ->

                }
            )

            /* ------------- Select Profession ------------ */
            DropDownField(
                selectedValue = selectedMaritalStatus.value,
                options = maritalStatusList,
                label = "Profession",
                Icons.Default.BusinessCenter,
                onValueChangedEvent = {
                    selectedMaritalStatus.value = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(text = "Next", background = Color.Black) {
                navController?.navigate("second_on_screen")
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    FirstOnboardingScreen(navController = null)
}