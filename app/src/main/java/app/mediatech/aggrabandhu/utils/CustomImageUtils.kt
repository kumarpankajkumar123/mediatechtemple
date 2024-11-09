package app.mediatech.aggrabandhu.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
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
import java.io.IOException

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

fun compressImage2(imageUri: Uri, context: Context): Uri? {
    var compressed: Uri? = null
    try {
        val imageBitmap: Bitmap =
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

        val compressedDisplayName = "compressed_${System.currentTimeMillis()}.jpg" // Unique display name

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, compressedDisplayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val contentResolver = context.contentResolver
        val compressedUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        compressedUri?.let {
            val outputStream = contentResolver.openOutputStream(it)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream!!)
            outputStream?.flush()
            outputStream?.close()

            compressed = compressedUri

            // Update the media scanner
            MediaScannerConnection.scanFile(context, arrayOf(compressedUri.toString()), null, null)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Log.e("error Compress", "${e.message} Catch IO")
    } catch (e: NullPointerException) {
        Log.e("error Compress", "${e.message} Catch Null")
    }
    return compressed
}

fun compressImage(imageUri: Uri, context: Context): Uri? {
    var compressed: Uri? = null
    try {
        // Load the bitmap and correct the rotation
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        val imageBitmap = BitmapFactory.decodeStream(inputStream)
        val rotatedBitmap = correctBitmapRotation(imageUri, imageBitmap, context)

        // Set up the output file details
        val compressedDisplayName = "compressed_${System.currentTimeMillis()}.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, compressedDisplayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val compressedUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        compressedUri?.let {
            val outputStream = contentResolver.openOutputStream(it)
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream!!)
            outputStream.flush()
            outputStream.close()
            compressed = compressedUri

            // Update the media scanner
            MediaScannerConnection.scanFile(context, arrayOf(compressedUri.toString()), null, null)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Log.e("error Compress", "${e.message} Catch IO")
    } catch (e: NullPointerException) {
        Log.e("error Compress", "${e.message} Catch Null")
    }
    return compressed
}

// Function to read Exif rotation and adjust the bitmap accordingly
private fun correctBitmapRotation(uri: Uri, bitmap: Bitmap, context: Context): Bitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
    val exif = inputStream?.use { ExifInterface(it) }
    val rotation = when (exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }
    inputStream?.close()

    // Rotate the bitmap if necessary
    return if (rotation != 0) {
        val matrix = Matrix()
        matrix.postRotate(rotation.toFloat())
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    } else {
        bitmap
    }
}