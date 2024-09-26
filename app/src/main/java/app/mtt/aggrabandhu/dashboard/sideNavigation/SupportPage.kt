package app.mtt.aggrabandhu.dashboard.sideNavigation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.dashboard.openSupport
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Preview
@Composable
fun SupportPage(navController: NavController?=null) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = "Support",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        Text(
            text = "Enquiry -",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        WhatsAppSupport(
            number = "7830305040",
            context,
            background = colorResource(id = R.color.white)
        )
        Text(
            text = "Follow us -",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 15.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.png_fb),
                contentDescription = "",
                modifier = Modifier
                    .size(75.dp)
                    .padding(horizontal = 10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.png_ig),
                contentDescription = "",
                modifier = Modifier
                    .size(75.dp)
                    .padding(horizontal = 10.dp)
            )
        }

        Text(
            text = "अग्रबंधु सेवार्थ संस्थान के कार्यालय की लोकेशन -",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        val atasehir = LatLng(40.9971, 29.1007)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(atasehir, 15f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(10.dp),
            cameraPositionState = cameraPositionState
        )
        // Declare a string that contains a url
        val mUrl = "https://maps.google.com/maps?q=26.9167° N,76.8159° E&amp;hl=es;z=14&amp;output=embed"

    }
}

@Composable
fun WhatsAppSupport(
    number: String?="7830305040",
    context: Context?=null,
    background : Color?= Color.White,
//    onClick: () -> Unit
) {
    Surface(shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = Modifier
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(background!!)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .clickable {
                openSupport(context!!, number!!)
            },
        shadowElevation = 3.dp
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth(0.76f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "WhatsApp Support",
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 18.sp,
                    style = TextStyle(
                        color = colorResource(id = R.color.green),
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .background(background)
                        .fillMaxWidth()
                )
                Text(
                    text = number!!,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 18.sp,
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .background(background)
                        .padding(5.dp, 8.dp)
                        .fillMaxWidth()
                )
            }
            Icon(
                imageVector = Icons.Default.Whatsapp,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(.9f)
                    .padding(5.dp),
                tint = colorResource(id = R.color.green)
            )
        }
    }
}