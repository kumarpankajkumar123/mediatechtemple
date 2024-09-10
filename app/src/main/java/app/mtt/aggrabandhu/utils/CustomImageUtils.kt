package app.mtt.aggrabandhu.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
            .padding(16.dp)
            .border(2.dp, Color.Black, CircleShape),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        alpha = DefaultAlpha,
    )
}