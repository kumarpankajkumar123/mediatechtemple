package app.mediatech.aggrabandhu.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Preview
@Composable
fun LoadingAlertDialog() {
    Dialog(onDismissRequest = {  }, properties = DialogProperties(
        dismissOnBackPress = false,dismissOnClickOutside = false
    )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(8.dp),
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            shadowElevation = 8.dp,
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please  wait!!",
                    modifier = Modifier.padding(8.dp), fontSize = 20.sp
                )
                Text(
                    text = "Loading...",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun LogoutDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = "Logout")
            },
            text = {
                Text("Are you sure you want to logout?")
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            }
        )
    }
}
