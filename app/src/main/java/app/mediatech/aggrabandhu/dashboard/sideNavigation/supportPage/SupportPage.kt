package app.mediatech.aggrabandhu.dashboard.sideNavigation.supportPage

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.dashboard.openSupport
import app.mediatech.aggrabandhu.utils.CustomButton
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState


const val FB = "https://www.facebook.com/aggrabandhusevarthsansthan/"
const val IG = "https://www.instagram.com/agrabandhusevarthsansthan"
const val YT = "https://www.youtube.com/@AggrabandhuSewaSansthan"

@Preview
@Composable
fun SupportPage(navController: NavController?=null, fromDashboard : Boolean?=false) {
    val context = LocalContext.current

    val supportViewmodel : SupportViewmodel = hiltViewModel()
    val sP = SharedPrefManager(context)

    supportViewmodel.init(sP)

    val supportResponse = supportViewmodel.supportResponse.collectAsState()

    val showProgressDialog = remember { mutableStateOf(false) }

    if (showProgressDialog.value){
        LoadingAlertDialog()
    }

    if (supportResponse.value != 0) {
        if (supportResponse.value == 200) {
            supportViewmodel.enquiryDescription = ""
            showProgressDialog.value = false
        } else {
            showProgressDialog.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {
        if(fromDashboard == false) {
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
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController?.popBackStack() }
                )
                Text(
                    text = "Support",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
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
                    .clickable {
                        intentToWeb(FB, context)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.png_ig),
                contentDescription = "",
                modifier = Modifier
                    .size(75.dp)
                    .padding(horizontal = 10.dp)
                    .clickable {
                        intentToWeb(IG, context)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.png_yt),
                contentDescription = "",
                modifier = Modifier
                    .size(75.dp)
                    .padding(horizontal = 10.dp)
                    .clickable {
                        intentToWeb(YT, context)
                    }
            )
        }

        Text(
            text = "Want help? Fill the form below",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        DescriptionBox(
            value = supportViewmodel.enquiryDescription,
            onValueChange = {supportViewmodel.enquiryDescription = it}
        ) {
            supportViewmodel.postEnquiry(context)
        }
    }
}

@Composable
fun DescriptionBox(
    value : String ?= "",
    onValueChange : (String) -> Unit,
    onButtonClick : () -> Unit,
) {
    // A Card to give the box a better appearance
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Label for the box
            Text(
                text = "Write your query below",
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TextField to enter description text
            TextFieldWithIcons(
                label = "Enter your query",
                placeholder = "Query",
                maxLength = 120,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.SupportAgent, 
                value = value
            ) {
                onValueChange.invoke(it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(text = "Submit", background = colorResource(id = R.color.orange)) {
                onButtonClick()
            }

        }
    }
}

@Composable
fun WhatsAppSupport(
    number: String?="7830305040",
    context: Context?=null,
    background : Color?= Color.White,
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

@Composable
private fun GMap() {
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
    val mUrl =
        "https://maps.google.com/maps?q=26.9167° N,76.8159° E&amp;hl=es;z=14&amp;output=embed"
}

fun intentToWeb(url:String, context : Context) {
    context.startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
    )
}