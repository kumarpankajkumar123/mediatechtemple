package app.mtt.aggrabandhu.authentication.onboarding

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.utils.CustomAlertDialog
import app.mtt.aggrabandhu.utils.CustomButton2
import app.mtt.aggrabandhu.utils.CustomCheckbox
import app.mtt.aggrabandhu.utils.DropDownField
import app.mtt.aggrabandhu.utils.SelectImageCardWithButton
import app.mtt.aggrabandhu.utils.SharedPrefManager
import app.mtt.aggrabandhu.utils.TextFieldWithIcons
import app.mtt.aggrabandhu.utils.prepareFilePart
import app.mtt.aggrabandhu.viewmodel.Onboarding2Viewmodel
import es.dmoral.toasty.Toasty

@Preview(showSystemUi = true)
@Composable
fun SecondOnboardingScreen(
    navController: NavController ?= null
) {

    val context = LocalContext.current

    val onboarding2Viewmodel : Onboarding2Viewmodel = hiltViewModel()
    val validationData = onboarding2Viewmodel.validateID.collectAsState()

    val referenceID = onboarding2Viewmodel.referenceID
    val getName = onboarding2Viewmodel.getName
    val phone = onboarding2Viewmodel.phone
    val password = onboarding2Viewmodel.password
    val profileUri = Uri.parse((onboarding2Viewmodel.profileUri))
    val father = onboarding2Viewmodel.father
    val mother = onboarding2Viewmodel.mother
    val gotra = onboarding2Viewmodel.gotra
    val maritalStatus = onboarding2Viewmodel.maritalStatus
    val dob = onboarding2Viewmodel.dob
    val profession = onboarding2Viewmodel.profession

    Toasty.success(context,phone).show()

    var adharNumber : String? = null

    val docsList = arrayListOf(
        "Pan card","Driving License", "Voter ID"
    )
    val selectedDoc = remember { mutableStateOf("") }
    val isSuffering = remember { mutableStateOf(false) }

//    if (profileUri.){
        Toasty.success(context,"dob").show()
//    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White)
    ) {

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
            label = "Adhar Card Number",
            placeholder = "Adhar card Nummber",
            maxLength = 12,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Newspaper
        ) {
            adharNumber = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        SelectImageCardWithButton(docType = "Aadhar Card") { uri ->
            val file = prepareFilePart(uri,"file",context)
            onboarding2Viewmodel.validateDoc(
                adharNumber!!,
                "aadhar card",
                file
            )
            Toast.makeText(context,"${validationData.value.valid} ${validationData.value.matched}", Toast.LENGTH_SHORT).show()
        }

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
                maxLength = if (selectedDoc.value == "DL") {
                    16
                } else {
                    10
                },
                keyboardType = KeyboardType.Password,
                leadingIcon = Icons.Default.Newspaper
            ) {

            }
            Spacer(modifier = Modifier.height(10.dp))
            SelectImageCardWithButton(selectedDoc.value){

            }
        }

        /*   - ------------ Nominee 1 ---------------- */
        TextFieldWithIcons(
            label = "Nominee",
            placeholder = "Nominee 1 Name",
            maxLength = 26,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Person2,
        ) { text ->

        }
        /*   ------------- Relation 1 ---------------- */
        TextFieldWithIcons(
            label = "Relation",
            placeholder = "Relation with Nominee 1",
            maxLength = 26,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.PeopleOutline,
        ) { text ->

        }
        // Spacer(modifier = Modifier.height(15.dp))
        /*   - ------------ Nominee 2 ---------------- */
        TextFieldWithIcons(
            label = "Nominee 2",
            placeholder = "Nominee 2 Name",
            maxLength = 26,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Person2,
        ) { text ->

        }
        // Spacer(modifier = Modifier.height(15.dp))
        /*   - ------------ Relation ---------------- */
        TextFieldWithIcons(
            label = "Relation",
            placeholder = "Relation with Nominee 2",
            maxLength = 26,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.PeopleOutline,
        ) { text ->

        }

        Spacer(modifier = Modifier.height(10.dp))

        CustomCheckbox(text = "Suffering From any disease?"){ isChecked ->
            isSuffering.value = isChecked
        }
        if (isSuffering.value){
            Spacer(modifier = Modifier.height(10.dp))
            SelectImageCardWithButton(docType = "Doctor certificate"){

            }
        }
        RulesAndRegulationsCheck(text = "Accept Rules and regulations"){

        }

        Spacer(modifier = Modifier.height(10.dp))

        CustomButton2(
            text = "Next",
            background = colorResource(id = R.color.orange)
        ) {
            navController?.navigate("dashboard_screen")
        }
    }
}

@Composable
fun RulesAndRegulationsCheck(
    text: String,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isOpened = remember { mutableStateOf(false) }
        val isChecked = remember { mutableStateOf(false) }

        if (isOpened.value) {
            CustomAlertDialog(onAccept = {
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
            text = text,
            fontWeight = FontWeight.SemiBold,
        )
    }
}