package app.mediatech.aggrabandhu.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import java.time.Instant
import java.time.ZoneId

@Composable
fun TextFieldWithIcons(
    label: String,
    placeholder: String,
    maxLength : Int,
    keyboardType: KeyboardType,
    leadingIcon: ImageVector,
    value : String?= "",
    modifier: Modifier = Modifier,
    onValueChanged : (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(value!!)) }
    val focusManager = LocalFocusManager.current
    // Update the text state if the passed value changes
    LaunchedEffect(value) {
        if (text.text != value) {
            text = TextFieldValue(value!!)
        }
    }
    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon( leadingIcon, contentDescription = "emailIcon",
            tint = Color.Black) },
        trailingIcon = {
            if (text.text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear Text",
                    modifier = Modifier.clickable {
                        text = TextFieldValue("")
                        onValueChanged.invoke("")
                    },
                    tint = Color.Black
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        onValueChange = { it ->
            if (it.text.length <= maxLength) {
                text = it
                onValueChanged.invoke(text.text)
            }
        },
        maxLines = 1,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
    )
}

/* --------------------------- Password Field -------------------------- */
@Composable
fun PasswordTextFieldWithIcons(
    label: String,
    placeholder: String,
    value : String ?= "",
    onValueChanged : (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(value!!)) }
    var passwordVisible by remember { mutableStateOf(false) }

    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon( Icons.Default.Lock, contentDescription = "userIcon",
            tint = Color.Black) },

        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        trailingIcon = {
            if (text.text.isNotEmpty()) {
                if (passwordVisible) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Password Visibility",
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                        tint = Color.Black
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "Password Visibility",
                        modifier = Modifier
                            .clickable { passwordVisible = !passwordVisible },
                        tint = Color.Black
                    )
                }
            }
        },
        onValueChange = {
            text = it
            onValueChanged.invoke(text.text)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

/* - -  - -------------- Drop Down ----------------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownField(
    selectedValue: String,
    options: List<String>,
    label: String,
    imageVector: ImageVector,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (options.isNotEmpty()) expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            leadingIcon = {
                Icon(imageVector = imageVector, contentDescription = "Leading Icon")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}

/* ---------------------- Date Picker ----------------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    value : String ?= "",
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val date = remember { mutableStateOf(value) }
    val isOpen = remember { mutableStateOf(false) }

        OutlinedTextField(
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    isOpen.value = true
                }
                .focusable(false),
            value = date.value!!,
            onValueChange = {},
            label = { Text(text = label) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Calendar Icon")
            },
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                //For Icons
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant)
        )

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
                isOpen.value = false // close dialog
                if (it != null) { // Set the date
                    date.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate().toString()
                    onClick.invoke(date.value!!)
                }
            },
            onCancel = {
                isOpen.value = false //close dialog
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text("Accept")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = state)
    }
}