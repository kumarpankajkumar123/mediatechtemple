package app.mtt.aggrabandhu.utils

import android.net.Uri
import android.util.Log
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.HtmlCompat
import app.mtt.aggrabandhu.R
import coil.compose.rememberAsyncImagePainter

@Composable
fun CustomButton(
    text: String,
    background : Color,
    onClick: () -> Unit
) {
    Surface(shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = Modifier
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(background)
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 18.sp,
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .background(background)
                .padding(5.dp, 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CustomButton2(
    text: String,
    background : Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = modifier
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(background)
            .padding(8.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 18.sp,
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .background(background)
                .padding(5.dp, 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun EditProfileButton(
    text: String? ="Edit Your Profile",
    background : Color ?= colorResource(id = R.color.orange),
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Surface(shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = modifier
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(background!!)
            .padding(4.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier.background(background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .background(background)
                    .size(18.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile",
                tint = Color.White
            )
            Text(
                text = text!!,
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 14.sp,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .background(background)
                    .padding(5.dp, 5.dp)
            )
        }
    }
}

@Composable
fun CustomButton3(
    text: String,
    background : Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = modifier
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(background)
            .padding(4.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 16.sp,
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .background(background)
                .padding(5.dp, 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CustomCheckbox(
    text: String,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isChecked = remember { mutableStateOf(false) }

        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                onClick(it)
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

@Composable
fun SelectImageCardWithButton(
    docType : String,
    modifier: Modifier = Modifier,
    onClick: (Uri) -> Unit
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                onClick.invoke(imageUri!!)
            }
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = if (imageUri != null) {
                rememberAsyncImagePainter(model = imageUri)
            } else rememberVectorPainter(
                image = Icons.Default.Image
            ) ,
            contentDescription = "",
            modifier = Modifier
                .widthIn(80.dp, 140.dp)
                .heightIn(80.dp, 120.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Select $docType Image")
            CustomButton(
                text = "Select Image",
                background = colorResource(id = R.color.orange)
            ) {
                galleryLauncher.launch("image/*")
            }
        }
    }
}

@Composable
fun CustomAlertDialog(
    heading : String,
    rules : String,
    onAccept: () -> Unit
) {
    Dialog(onDismissRequest = { onAccept() }, properties = DialogProperties(
        dismissOnBackPress = false,dismissOnClickOutside = false
    )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(150.dp, 750.dp)
                .padding(8.dp),
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            shadowElevation = 8.dp,
        ) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = heading,
                    modifier = Modifier.padding(8.dp), fontSize = 20.sp
                )
//                Text(
//                    text = rules,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth()
//                )
                HtmlText(html = rules, modifier = Modifier.padding(8.dp))
                OutlinedButton(
                    onClick = { onAccept() },
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Accept")
                }
            }
        }
    }
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}
@Preview
@Composable
private fun Preview() {
//    CustomAlertDialog {}
}