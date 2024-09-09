package app.mtt.aggrabandhu.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircularImage (width : Dp, painter: Painter) {
    Image(
        // first parameter of our Image
        // is our image path which we have created
        // above
        painter = painter,
        contentDescription = "Sample Image",

        // below line is used for creating a modifier for our image
        // which includes image size, padding and border
        modifier = Modifier
            .width(width)
            .padding(16.dp)
            .border(2.dp, Color.Black, CircleShape),

        // below line is used to give
        // alignment to our image view.
        alignment = Alignment.Center,

        // below line is used to scale our image
        // we are using center crop for it.
        contentScale = ContentScale.Crop,

        // below line is used to define the opacity of the image.
        // Here, it is set to the default alpha value, DefaultAlpha.
        alpha = DefaultAlpha,
    )
}