package app.mtt.aggrabandhu.dashboard.pages.liveDonation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mtt.aggrabandhu.dashboard.sideNavigation.TextDetails
import app.mtt.aggrabandhu.utils.CircularImage
import app.mtt.aggrabandhu.utils.CustomButton2
import app.mtt.aggrabandhu.utils.CustomButton3
import app.mtt.aggrabandhu.utils.LoadingAlertDialog
import app.mtt.aggrabandhu.viewmodel.LiveDonationsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DonationsPage(navController: NavController) {

    val liveDonationsViewModel : LiveDonationsViewModel = hiltViewModel()
    val liveDonationList = liveDonationsViewModel.liveDonationsData.collectAsState()

    if (liveDonationList.value.isEmpty()){
        LoadingAlertDialog()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "All Live Donations",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        LazyColumn(content = {
            items(liveDonationList.value){
                LiveDonations(it, navController)
            }
        })
    }
}

@Composable
private fun LiveDonations (
    liveDonationData: LiveDonationData,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Donate to",
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 5.dp),
                fontWeight = FontWeight.Light,
                color = Color.White
            )
            Text(
                text = liveDonationData.Member.name,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 2.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 1
            )
        }

        Row(modifier = Modifier
            .padding(bottom = 6.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(0.6f)
            ) {
                TextDetails(ques = "Name", ans = liveDonationData.Member.name)
                TextDetails(ques = "Member ID", ans = liveDonationData.member_id.toString())
                TextDetails(ques = "Address", ans = "${liveDonationData.Member.district}, ${liveDonationData.Member.state}")
                TextDetails(ques = "Bank", ans = "")
                TextDetails(ques = "Start Date", ans = convertDateFormat(liveDonationData.start_date)!!)
                TextDetails(ques = "End Date", ans = convertDateFormat(liveDonationData.end_date)!!)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularImage(
                        size = 54.dp,
                        painter = rememberVectorPainter(image = Icons.Default.PersonPin)
//                        painter = if (liveDonationData.img != null) {
//                            painterResource(id = donorsData.img)
//                        } else rememberVectorPainter(
//                            image = donorsData.imageVector!!
//                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(CornerSize(size = 10.dp)),
                        modifier = Modifier
                            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
                            .width(85.dp)
                            .background(Color.Black)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = liveDonationData.status,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            fontSize = 14.sp,
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .background(Color.Black)
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }

        CustomButton3(
            text = "Click to Donate",
            background = Color.Black,
            modifier = Modifier
                .height(45.dp)
        ) {
            navController.navigate("make_donation_screen")
        }
    }
}

fun convertDateFormat(dateString: String) : String? {
    // Input date format
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    // Output date format (dd/MM/yy)
    val outputFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

    // Parse the input date string
    val date: Date? = inputFormat.parse(dateString)

    // Format the date to dd/MM/yy
    val formattedDate = date?.let { outputFormat.format(it) }

    return formattedDate
}