package app.mtt.aggrabandhu.authentication.onboarding

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mtt.aggrabandhu.utils.CustomAlertDialog
import app.mtt.aggrabandhu.utils.CustomButton2
import app.mtt.aggrabandhu.utils.CustomCheckbox
import app.mtt.aggrabandhu.utils.DropDownField
import app.mtt.aggrabandhu.utils.SelectImageCardWithButton
import app.mtt.aggrabandhu.utils.TextFieldWithIcons

@Preview(showSystemUi = true)
@Composable
fun SecondOnboardingScreen(
    navController: NavController ?= null
) {

    val docsList = arrayListOf(
        "PAN","DL"
    )
    val selectedDoc = remember { mutableStateOf("") }
    val isSuffering = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White)
    ) {

        /*   - ------------ Pin Code ---------------- */
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
            
        }
        Spacer(modifier = Modifier.height(10.dp))

        SelectImageCardWithButton(docType = "Aadhar Card") {

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
            background = Color.Black
        ) {

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