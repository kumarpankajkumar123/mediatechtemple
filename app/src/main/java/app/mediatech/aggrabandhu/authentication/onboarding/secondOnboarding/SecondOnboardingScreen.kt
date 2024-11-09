package app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import app.mediatech.aggrabandhu.authentication.FileRequestBody
import app.mediatech.aggrabandhu.utils.CustomAlertDialog
import app.mediatech.aggrabandhu.utils.CustomButton2
import app.mediatech.aggrabandhu.utils.CustomCheckbox
import app.mediatech.aggrabandhu.utils.DropDownField
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SelectImageCardWithButton
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons
import app.mediatech.aggrabandhu.utils.compressImage
import app.mediatech.aggrabandhu.utils.prepareFilePart
import app.mediatech.aggrabandhu.viewmodel.Onboarding2Viewmodel
import es.dmoral.toasty.Toasty
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@Preview(showSystemUi = true)
@Composable
fun SecondOnboardingScreen(
    navController: NavController ?= null
) {
    val context = LocalContext.current

    val onboarding2Viewmodel : Onboarding2Viewmodel = hiltViewModel()

    onboarding2Viewmodel.initSharedPref(context)

    val validation = onboarding2Viewmodel.validateID.collectAsState()
    val validationMessage = remember { mutableStateOf("") }
    val validationAdha = remember { mutableIntStateOf(1) }

    val otherDocValidation = onboarding2Viewmodel.validateOtherID.collectAsState()
    val validationOther = remember { mutableIntStateOf(1) }
    val validationOtherMessage = remember { mutableStateOf("") }

    val rules = onboarding2Viewmodel.rules.collectAsState()
    val declaration = onboarding2Viewmodel.declaration.collectAsState()

    val city = onboarding2Viewmodel.city.collectAsState()
    val state = onboarding2Viewmodel.state.collectAsState()

//    if (rules.value == "wait") {
//        LoadingAlertDialog()
//    }

    val signupResponse = onboarding2Viewmodel.signupResponse.collectAsState()
    val signupResponseCode = onboarding2Viewmodel.signupResponseCode.collectAsState()

    val postalData = onboarding2Viewmodel.tahsilList.collectAsState()
    val selectedPostal = onboarding2Viewmodel.selectedTahsil.collectAsState()

    val sp = SharedPrefManager(context)

    var isAdharNumb by remember { mutableStateOf("") }
    var isDocNumb by remember { mutableStateOf("") }

    val docsList = arrayListOf(
        "PAN Card","Driving License", "Voter ID"
    )
    val selectedDoc = remember { mutableStateOf("") }
    val isSuffering = remember { mutableStateOf(false) }

    val selectedCity = remember { mutableStateOf("") }
    val selectedState = remember {
        mutableStateOf("")
    }

    val showProgressDialog = remember { mutableStateOf(false) }

    if (validation.value != 0) {
        if (validation.value == 200) {
            onboarding2Viewmodel.isAdharVerified = true
            if (validationAdha.intValue == 0) {
                showProgressDialog.value = false
                validationMessage.value = "Verified"
                Toasty.success(context, "Verified", Toast.LENGTH_SHORT).show()
                validationAdha.intValue = 1
            }
        } else if (validation.value == 406) {
            onboarding2Viewmodel.isAdharVerified = false
            if (validationAdha.intValue == 0) {
                showProgressDialog.value = false
                validationMessage.value = "This id already exist"
                Toasty.error(context, "This id already exist", Toast.LENGTH_SHORT).show()
                validationAdha.intValue = 1
            }
        } else {
            onboarding2Viewmodel.isAdharVerified = false
            if (validationAdha.intValue == 0) {
                showProgressDialog.value = false
                validationMessage.value = "Reselect Image"
                Toasty.error(context, "Reselect Image", Toast.LENGTH_SHORT).show()
                validationAdha.intValue = 1
            }
        }
    }

    if (otherDocValidation.value != 0) {
        if (otherDocValidation.value == 200) {
            onboarding2Viewmodel.isOtherDocVerified = true
            if (validationOther.intValue == 0) {
                showProgressDialog.value = false
                validationOther.intValue = 1
                validationOtherMessage.value = "Verified"
                Toasty.success(context, "Verified", Toast.LENGTH_SHORT).show()
            }
        } else if (otherDocValidation.value == 406) {
            onboarding2Viewmodel.isOtherDocVerified = false
            if (validationOther.intValue == 0) {
                showProgressDialog.value = false
                validationOther.intValue = 1
                validationOtherMessage.value = "This id already exist"
                Toasty.error(context, "This id already exist", Toast.LENGTH_SHORT).show()
            }
        } else {
            onboarding2Viewmodel.isOtherDocVerified = true
            if (validationOther.intValue == 0) {
                showProgressDialog.value = false
                validationOther.intValue = 1
                validationOtherMessage.value = "Verified"
//            Toasty.error(context, "Reselect Image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (postalData.value.Status == "Success"){
        onboarding2Viewmodel.cityTextChanged(postalData.value.PostOffice[0].District)
        onboarding2Viewmodel.stateTextChanged(postalData.value.PostOffice[0].State)
        selectedCity.value = (postalData.value.PostOffice[0].District)
        selectedState.value = (postalData.value.PostOffice[0].State)
    } else if (postalData.value.Status == "Error"){
            Toasty.error(context, "No records found", Toast.LENGTH_SHORT).show()
    }

    if (signupResponseCode.value != 0) {
        if (!onboarding2Viewmodel.isSignUp) {
            when (signupResponseCode.value) {
                200 -> {
                    showProgressDialog.value = false
                    onboarding2Viewmodel.isSignUp = true
                    sp.saveLoginStatus(true)
                    sp.saveProfileUrl(signupResponse.value.memberAdd!!.profileUrl)
                    Log.d("userID", signupResponse.value.memberAdd?.id.toString())
                    Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show()
                        navController?.navigate("dashboard_screen"){
                            popUpTo("dashboard_screen"){
                                inclusive = true
                            }
                        }
                }
                406 -> {
                    showProgressDialog.value = false
                    onboarding2Viewmodel.isSignUp = false
                    Toasty.error(context, "Reference ID is wrong", Toast.LENGTH_SHORT).show()
                }
                413 -> {
                    showProgressDialog.value = false
                    onboarding2Viewmodel.isSignUp = false
                    Toasty.error(context, "Image size is too large", Toast.LENGTH_SHORT).show()
                }
                else-> {
                    showProgressDialog.value = false
                    onboarding2Viewmodel.isSignUp = false
                    Toasty.error(context, "Retry - Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (showProgressDialog.value){
        LoadingAlertDialog()
    }

    val profileUri : Uri = Uri.parse(SharedPrefManager(context).getProfileImageUri())

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White)
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
                        navController?.popBackStack()
                    }
            )
        }

        /*   ------------- Pin Code ---------------- */
        TextFieldWithIcons(
            label = "Pin Code",
            placeholder = "Area Pin Code",
            maxLength = 6,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.PinDrop,
        ) { text ->
            onboarding2Viewmodel.pincode = text
            if (text.length == 6) {
                onboarding2Viewmodel.getPostalData(text)
            }
        }
        // Spacer(modifier = Modifier.height(15.dp))

        DropDownField(
            selectedValue = selectedState.value,
            options = emptyList(),
            label = "State",
            Icons.Default.LocationOn,
//            Icons.Default.PermContactCalendar,
            onValueChangedEvent = {

            }
        )
        DropDownField(
            selectedValue = selectedCity.value,
            options = emptyList(),
            label = "City",
            Icons.Default.LocationCity,
//            Icons.Default.PermContactCalendar,
            onValueChangedEvent = {

            }
        )
        DropDownField (
            selectedValue = selectedPostal.value,
            options = postalData.value.PostOffice.map { it.Name },
            label = "Tahsil",
            imageVector = Icons.Default.LocationCity,
            onValueChangedEvent = {
                onboarding2Viewmodel.selectedTahsilChanged(it)
                onboarding2Viewmodel.cityTextChanged(postalData.value.PostOffice[0].District)
                onboarding2Viewmodel.stateTextChanged(postalData.value.PostOffice[0].State)

                selectedCity.value = (postalData.value.PostOffice[0].District)
                selectedState.value = (postalData.value.PostOffice[0].State)
                Log.d("City", "${postalData.value.PostOffice[0].District}")
            }
        )
        /* ------------- City ---------------- */
//        TextFieldWithIcons(
//            label = "City",
//            placeholder = "City",
//            maxLength = 26,
//            keyboardType = KeyboardType.Text,
//            leadingIcon =
//        ) { text ->
//            onboarding2Viewmodel.cityTextChanged(text)
//        }
        // Spacer(modifier = Modifier.height(15.dp))
        /*   - ------------ State ---------------- */
//        TextFieldWithIcons(
//            label = "State",
//            placeholder = "State",
//            maxLength = 26,
//            keyboardType = KeyboardType.Text,
//            leadingIcon = Icons.Default.LocationOn,
//            selectedState.value
//        ) { text ->
//            onboarding2Viewmodel.stateTextChanged(text)
//        }
        /* ------------- Address ---------------- */
        TextFieldWithIcons(
            label = "Address",
            placeholder = "Full Address",
            maxLength = 46,
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Default.LocationOn,
        ) { text ->
            onboarding2Viewmodel.address = text
        }

        /* ------------- Address ---------------- */
        TextFieldWithIcons(
            label = "Email (OPTIONAL)",
            placeholder = "Email (OPTIONAL)",
            maxLength = 26,
            keyboardType = KeyboardType.Email,
            isRequired = false,
            leadingIcon = Icons.Default.LocationOn,
        ) { text ->
            onboarding2Viewmodel.email = text
        }

        TextFieldWithIcons(
            label = "Aadhar Card Number",
            placeholder = "Aadhar card Number",
            maxLength = 12,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Newspaper
        ) {
            isAdharNumb = it
            onboarding2Viewmodel.adharNumber = it
        }

        if (isAdharNumb.length == 12) {
            Spacer(modifier = Modifier.height(10.dp))

            SelectImageCardWithButton(docType = "Aadhar Card") { uri ->
                val compressed = compressImage(uri, context)
//                val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
//                    compressed!!,"r",null
//                )?: return@SelectImageCardWithButton
//
//                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//                val file =  File(context.cacheDir, "cropped_Profile${System.currentTimeMillis()}.jpg")
//                val outputStream = FileOutputStream(file)
//                inputStream.copyTo(outputStream)
//                val body = FileRequestBody(file,"file")
//
//                val img = MultipartBody.Part.createFormData("file", file.name, body)

                onboarding2Viewmodel.adharUri = compressed
                validationAdha.intValue = 0
                showProgressDialog.value = true
//                onboarding2Viewmodel.file = img
                onboarding2Viewmodel.file = prepareFilePart(compressed!!, "file", context)
                onboarding2Viewmodel.validateDoc(
                    onboarding2Viewmodel.adharNumber!!,
                    "aadhar card",
                    onboarding2Viewmodel.file!!
                )
            }
        }
        Text(
            text = validationMessage.value,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 12.sp,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp)
        )

        /* ------------- Select Document Type ------------ */
        DropDownField(
            selectedValue = selectedDoc.value,
            options = docsList,
            label = "Please Upload Documents",
            Icons.Default.Newspaper,
            onValueChangedEvent = {
                selectedDoc.value = it
            }
        )

        if (selectedDoc.value != "") {
            TextFieldWithIcons(
                label = "Enter ${selectedDoc.value} Number",
                placeholder = "${selectedDoc.value} Number",
                maxLength = if (selectedDoc.value == "Driving License") {
                    16
                } else {
                    10
                },
                keyboardType = KeyboardType.Password,
                leadingIcon = Icons.Default.Newspaper
            ) {
                isDocNumb = it
                onboarding2Viewmodel.idNumber = it
            }

            if ((selectedDoc.value == "Driving License" && isDocNumb.length == 16)) {
                Spacer(modifier = Modifier.height(10.dp))
                SelectImageCardWithButton(selectedDoc.value) {
                    val compressed = compressImageToUri(it, context)
//                    val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
//                        compressed!!,"r",null
//                    )?: return@SelectImageCardWithButton
//
//                    val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//                    val file =  File(context.cacheDir, "cropped_Profile${System.currentTimeMillis()}.jpg")
//                    val outputStream = FileOutputStream(file)
//                    inputStream.copyTo(outputStream)
//                    val body = FileRequestBody(file,"file2")
//
//                    val img = MultipartBody.Part.createFormData("file", file.name, body)
//                    val img2 = MultipartBody.Part.createFormData("file2", file.name, body)

                    onboarding2Viewmodel.panUri = compressed
                    onboarding2Viewmodel.file2 = prepareFilePart(compressed!!, "file2", context)

                    showProgressDialog.value = true
                    onboarding2Viewmodel.docFile = prepareFilePart(compressed, "file", context)

                    onboarding2Viewmodel.validateOtherDoc(
                        onboarding2Viewmodel.idNumber!!,
                        if (selectedDoc.value == "PAN Card") {
                            "Pan card"
                        } else {
                            selectedDoc.value
                        },
                        onboarding2Viewmodel.docFile!!
                    )
                    validationOther.intValue = 0
                }
            } else if ((selectedDoc.value == "Voter ID" || selectedDoc.value == "PAN Card") && isDocNumb.length == 10) {
                Spacer(modifier = Modifier.height(10.dp))
                SelectImageCardWithButton(selectedDoc.value) {
                    val compressed = compressImage(it, context)
//                    val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
//                        compressed!!,"r",null
//                    )?: return@SelectImageCardWithButton
//
//                    val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//                    val file =  File(context.cacheDir, "cropped_Profile${System.currentTimeMillis()}.jpg")
//                    val outputStream = FileOutputStream(file)
//                    inputStream.copyTo(outputStream)
//                    val body = FileRequestBody(file,"file2")
//
//                    val img = MultipartBody.Part.createFormData("file", file.name, body)
//                    val img2 = MultipartBody.Part.createFormData("file2", file.name, body)

                    onboarding2Viewmodel.panUri = compressed
//                    onboarding2Viewmodel.file2 = img2
                    onboarding2Viewmodel.file2 = prepareFilePart(compressed!!, "file2", context)

                    showProgressDialog.value = true
                    onboarding2Viewmodel.docFile = prepareFilePart(compressed, "file", context)

                    onboarding2Viewmodel.validateOtherDoc(
                        onboarding2Viewmodel.idNumber!!,
                        if (selectedDoc.value == "PAN Card") {
                            "Pan card"
                        } else {
                            selectedDoc.value
                        },
                        onboarding2Viewmodel.docFile!!
                    )
                    validationOther.intValue = 0
                }
            }
        }
        Text(
            text = validationOtherMessage.value,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 12.sp,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp, 5.dp)
        )

        /*   - ------------ Nominee 1 ---------------- */
        TextFieldWithIcons(
            label = "Nominee",
            placeholder = "Nominee 1 Name",
            maxLength = 46,
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Default.Person2
        ) { text ->
            onboarding2Viewmodel.nominee = text
        }
        /*   ------------- Relation 1 ---------------- */
        TextFieldWithIcons(
            label = "Relation",
            placeholder = "Relation with Nominee 1",
            maxLength = 46,
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Default.PeopleOutline,
        ) { text ->
            onboarding2Viewmodel.relation = text
        }
        // Spacer(modifier = Modifier.height(15.dp))
        /*   - ------------ Nominee 2 ---------------- */
        TextFieldWithIcons(
            label = "Nominee 2",
            placeholder = "Nominee 2 Name (OPTIONAL)",
            maxLength = 46,
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Default.Person2,
            isRequired = false
        ) { text ->
            onboarding2Viewmodel.nominee2 = text
        }
        // Spacer(modifier = Modifier.height(15.dp))
        /*   - ------------ Relation ---------------- */
        TextFieldWithIcons(
            label = "Relation",
            placeholder = "Relation with Nominee 2 (OPTIONAL)",
            maxLength = 46,
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Default.PeopleOutline,
            isRequired = false
        ) { text ->
            onboarding2Viewmodel.relation2 = text
        }

        Spacer(modifier = Modifier.height(10.dp))

        CustomCheckbox(text = "Suffering From any disease?"){ isChecked ->
            isSuffering.value = isChecked
            onboarding2Viewmodel.isDisease = isChecked
        }
        if (isSuffering.value){
            Spacer(modifier = Modifier.height(10.dp))
            SelectImageCardWithButton(docType = "Doctor certificate"){
                onboarding2Viewmodel.diseaseFile = prepareFilePart(it, "diseaseFile", context)
            }
        } else {
            onboarding2Viewmodel.diseaseFile = null
        }
        RulesAndRegulationsCheck(rules = rules.value, checkBoxHeading = "Accept Rules and regulations", dialogHeading = "Rules and Regulations"){
            onboarding2Viewmodel.isRuleAccepted = it
            Log.d("Rules", "${onboarding2Viewmodel.isRuleAccepted}")
        }
        RulesAndRegulationsCheck(rules = declaration.value, checkBoxHeading = "Self Declaration", dialogHeading = "Self Declaration"){
            Log.d("S-Declaration", "$it")
            onboarding2Viewmodel.isDeclaration = it
            Log.d("Rules", "${onboarding2Viewmodel.isRuleAccepted}")
        }

        Spacer(modifier = Modifier.height(10.dp))

        CustomButton2(
            text = "Submit",
            background = colorResource(id = R.color.orange)
        ) {
            if (onboarding2Viewmodel.pincode!!.length < 6){
                Toasty.error(context, "Please enter pin code", Toast.LENGTH_SHORT).show()
            } else if (city.value.isEmpty()){
                Toasty.error(context, "Please enter city", Toast.LENGTH_SHORT).show()
            } else if (state.value.isEmpty()){
                Toasty.error(context, "Please enter state", Toast.LENGTH_SHORT).show()
            } else if (onboarding2Viewmodel.address!!.isEmpty()){
                Toasty.error(context, "Please enter address", Toast.LENGTH_SHORT).show()
            } else if (onboarding2Viewmodel.adharNumber!!.isEmpty()){
                Toasty.error(context, "Please enter adhar number", Toast.LENGTH_SHORT).show()
            } else if (onboarding2Viewmodel.nominee!!.isEmpty()){
                Toasty.error(context, "Please enter nominee name", Toast.LENGTH_SHORT).show()
            } else if (onboarding2Viewmodel.relation!!.isEmpty()){
                Toasty.error(context, "Please enter relation with nominee", Toast.LENGTH_SHORT).show()
//            } else if (onboarding2Viewmodel.nominee2!!.isEmpty()){
//                Toasty.error(context, "Please enter nominee name", Toast.LENGTH_SHORT).show()
//            } else if (onboarding2Viewmodel.relation2!!.isEmpty()){
//                Toasty.error(context, "Please enter relation with nominee", Toast.LENGTH_SHORT).show()
            } else if (!onboarding2Viewmodel.isRuleAccepted) {
                Toasty.error(context, "Please Accept Rules  First", Toast.LENGTH_SHORT).show()
            } else if (!onboarding2Viewmodel.isAdharVerified) {
                Toasty.error(context, "Please select an valid adhar First", Toast.LENGTH_SHORT).show()
            } else if (!onboarding2Viewmodel.isOtherDocVerified) {
                Toasty.error(context, "Please select an valid Other Doc First", Toast.LENGTH_SHORT).show()
            } else if (!onboarding2Viewmodel.isDeclaration) {
                Toasty.error(context, "Self Declaration is required", Toast.LENGTH_SHORT).show()
            } else {
//                val compress = compressImageToUri2(profileUri, context)
//                val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
//                    profileUri,"r",null
//                )?: return@CustomButton2
//
//                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//                val file =  File(context.cacheDir, "cropped_Profile${System.currentTimeMillis()}.jpg")
//                val outputStream = FileOutputStream(file)
//                inputStream.copyTo(outputStream)
//                val body = FileRequestBody(file,"profile")
//
//                val img = MultipartBody.Part.createFormData("profile", file.name, body)

//                onboarding2Viewmodel.profileFile = img
                onboarding2Viewmodel.profileFile = prepareFilePart(profileUri, "profile", context)
                if (onboarding2Viewmodel.isDisease) {
                    if (onboarding2Viewmodel.diseaseFile != null) {
                        showProgressDialog.value = true
                        onboarding2Viewmodel.signUpWith(
                            selectedDoc.value,
                            "${onboarding2Viewmodel.isRuleAccepted}",
                            context
                        )
                    } else {
                        Toasty.error(
                            context,
                            "Please Select Doctor Certificate",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    showProgressDialog.value = true
                    onboarding2Viewmodel.signUp(
                        selectedDoc.value,
                        "${onboarding2Viewmodel.isRuleAccepted}",
                        context
                    )
                }
            }
        }
    }
}

@Composable
fun RulesAndRegulationsCheck(
    rules: String,
    checkBoxHeading: String,
    dialogHeading: String,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isOpened = remember { mutableStateOf(false) }
        val isChecked = remember { mutableStateOf(false) }

        if (isOpened.value) {
            CustomAlertDialog(
                dialogHeading,
                rules = rules,
                onAccept = {
                isOpened.value = false
                isChecked.value = true
            })
        }

        Checkbox (
            checked = isChecked.value,
            onCheckedChange = {
                if (it) {
                    isOpened.value = it
                } else {
                    isChecked.value = false
                }
                onClick.invoke(it)
            },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Black)
        )
        Text(
            text = checkBoxHeading,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

fun compressImageToUri(imageUri: Uri, context: Context): Uri? {
    // Retrieve the image file path from the URI
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(imageUri, filePathColumn, null, null, null)
    cursor?.moveToFirst()

    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
    val picturePath = columnIndex?.let { cursor.getString(it) }
    cursor?.close()

    if (picturePath == null) {
        return null // If picturePath is null, return
    }

    // Read the Exif data to get the original orientation of the image
    val exif = ExifInterface(picturePath)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

    // Decode the image into a bitmap
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(picturePath, options)

    options.inJustDecodeBounds = false
    options.inSampleSize = calculateInSampleSize(options, 800, 800) // Resize the image

    val originalBitmap = BitmapFactory.decodeFile(picturePath, options)

    // Rotate the bitmap if necessary
    val rotatedBitmap = rotateBitmapIfRequired(originalBitmap, orientation)

    // Create a unique filename for each compressed image
    val uniqueFileName = "compressed_image_${System.currentTimeMillis()}.jpg"
    val compressedImageFile = File(context.cacheDir, uniqueFileName)

    try {
        // Compress the rotated bitmap and write it to the file
        val outputStream = FileOutputStream(compressedImageFile)
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Compress with 80% quality
        outputStream.flush()
        outputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    // Return the URI for the compressed image file
    return Uri.fromFile(compressedImageFile)
}

fun compressImageToUri2(uri: Uri, context: Context): Uri? {
    return try {
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        inputStream?.use {
            var bitmap = BitmapFactory.decodeStream(it)

            // Correct the rotation if necessary
            val rotation = getImageRotation(uri, context)
            if (rotation != 0) {
                bitmap = rotateBitmap(bitmap, rotation)
            }

            // Resize the bitmap (reduce resolution by half in this example)
            bitmap = resizeBitmap(bitmap, maxWidth = 800, maxHeight = 800)

            // Compress the resized bitmap with lower quality
            val compressedBitmap = compressBitmap(bitmap, quality = 30)

            // Save compressed bitmap to cache directory
            val cacheDir = context.cacheDir
            val compressedFile = File(cacheDir, "compressed_image_${System.currentTimeMillis()}.jpg")
            compressedFile.outputStream().use { fileOutputStream ->
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream)
            }
            Uri.fromFile(compressedFile)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
// Function to resize the bitmap
private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    val (newWidth, newHeight) = if (bitmap.width > bitmap.height) {
        maxWidth to (maxWidth / aspectRatio).toInt()
    } else {
        (maxHeight * aspectRatio).toInt() to maxHeight
    }
    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
}

// Function to get the rotation from Exif data
private fun getImageRotation(uri: Uri, context: Context): Int {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use {
            val exif = ExifInterface(it)
            when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } ?: 0
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

// Function to rotate bitmap
private fun rotateBitmap(bitmap: Bitmap, rotation: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(rotation.toFloat())
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

// Function to compress the bitmap with a specific quality
private fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    return BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.size())
}

// Function to rotate the bitmap if required
fun rotateBitmapIfRequired(bitmap: Bitmap, orientation: Int): Bitmap {
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        else -> return bitmap // No rotation needed
    }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}