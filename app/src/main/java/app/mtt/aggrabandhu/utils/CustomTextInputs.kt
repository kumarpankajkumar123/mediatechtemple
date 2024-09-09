package app.mtt.aggrabandhu.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldWithIcons(
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    onValueChanged : (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current

    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon( leadingIcon, contentDescription = "emailIcon",
            tint = MaterialTheme.colorScheme.surfaceTint) },
        trailingIcon = {
            if (text.text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable { text = TextFieldValue("") },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        onValueChange = {
            text = it
            onValueChanged.invoke(text.text)
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun PasswordTextFieldWithIcons(
    onValueChanged : (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }


    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon( Icons.Default.Person, contentDescription = "userIcon",
            tint = MaterialTheme.colorScheme.surfaceTint) },

        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        trailingIcon = {
            if (text.text.isNotEmpty()) {
                if (passwordVisible) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        onValueChange = {
            text = it
            onValueChanged.invoke(text.text)
        },
        label = { Text(text = "Password") },
        placeholder = { Text(text = "Enter Password") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
