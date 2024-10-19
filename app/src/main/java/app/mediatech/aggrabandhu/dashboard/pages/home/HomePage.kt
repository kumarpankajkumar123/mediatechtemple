package app.mediatech.aggrabandhu.dashboard.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.convertDateFormat
import app.mediatech.aggrabandhu.utils.CircularImage
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog

@Composable
fun HomePage() {

    val homeViewModel : HomeViewModel = hiltViewModel()

    val notificationData = homeViewModel.allNotifications.collectAsState()
    val notificationCode = homeViewModel.notificationCode.collectAsState()

    if (notificationCode.value == 0) {
        LoadingAlertDialog()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Notifications",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyColumn(content = {
            items(notificationData.value){
                NotificationCard(notificationData = it)
            }
        })
    }
}

@Composable
fun NotificationCard (notificationData: Data) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier
            .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(size = 50.dp, painter = painterResource(id = R.drawable.png_logo))
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = notificationData.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                    Text(
                        text = convertDateFormat(notificationData.updatedAt).toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                }
        }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notificationData.content,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    HomePage()
}