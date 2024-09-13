package app.mtt.aggrabandhu.dashboard.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.utils.CircularImage

@Composable
fun ProfilePage() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.textured_bg),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
        )
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 55.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(size = 220.dp, painter = painterResource(id = R.drawable.png_logo))
        ProfileInfoCard(Icons.Default.Person, heading = "Name", text = "The Person")
        ProfileInfoCard(Icons.Default.PhoneAndroid, heading = "Phone", text = "9199103223")
        ProfileInfoCard(Icons.Default.CalendarMonth, heading = "DOB", text = "13/13/3223")
        ProfileInfoCard(Icons.Default.CalendarMonth, heading = "DOB", text = "13/13/3223")
        ProfileInfoCard(Icons.Default.Person, heading = "Name", text = "The Person")
        ProfileInfoCard(Icons.Default.CalendarMonth, heading = "DOB", text = "13/13/3223")
        ProfileInfoCard(Icons.Default.CalendarMonth, heading = "DOB", text = "13/13/3223")
        }
}

@Composable
fun ProfileInfoCard(
    imageVector: ImageVector,
    heading : String,
    text : String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "",
                modifier = Modifier.size(34.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = heading,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ProfilePage()
}
