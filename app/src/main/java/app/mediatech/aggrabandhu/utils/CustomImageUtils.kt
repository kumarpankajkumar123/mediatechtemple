package app.mediatech.aggrabandhu.utils

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@Composable
fun CircularImage (
    size : Dp,
    painter: Painter
){
    Image(
        // first parameter of our Image
        painter = painter,
        contentDescription = "Sample Image",
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
//            .padding(6.dp)
            .border(2.dp, Color.Black, CircleShape),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        alpha = DefaultAlpha,
    )
}

fun prepareFilePart(fileUri: Uri, schema : String, context: Context): MultipartBody.Part {
    val filesDir = context.filesDir
    val file = File(filesDir,"${getRandomString(6)}.png")

    val inputStream = context.contentResolver.openInputStream(fileUri)
    val outputStream = FileOutputStream(file)
    inputStream!!.copyTo(outputStream)

    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(schema, file.name, requestBody)
}

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}