package app.mtt.aggrabandhu.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mtt.aggrabandhu.R


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