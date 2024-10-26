package app.mediatech.aggrabandhu.dashboard.pages.liveDonation

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations.TextDetails
import app.mediatech.aggrabandhu.di.baseUrl
import app.mediatech.aggrabandhu.utils.CircularImage
import app.mediatech.aggrabandhu.utils.CustomButton3
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.viewmodel.LiveDonationsViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DonationsPage(navController: NavController, liveDonationsViewModel: LiveDonationsViewModel?=null) {

    val context = LocalContext.current

    val liveDonationList = liveDonationsViewModel!!.liveDonationsData.collectAsState()
    val responseCode = liveDonationsViewModel.responseCode.collectAsState()

    if (responseCode.value == 0){
        LoadingAlertDialog()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Live Donations",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        if (liveDonationList.value.isEmpty()){
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 10.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "No Data",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
        LazyColumn(content = {
            items(liveDonationList.value){
                LiveDonations(it, navController, context)
            }
        })
    }
}

@Composable
private fun LiveDonations (
    liveDonationData: LiveDonationData,
    navController: NavController,
    context: Context
) {
    val sharedPref = SharedPrefManager(context)

    // Assuming you have the ApiResponse object from the API call
    val gson = Gson()
    val bankDetail: BankDetail ?= try {
        gson.fromJson(liveDonationData.bank_detail, BankDetail::class.java)
    } catch (e:Exception){
        e.printStackTrace()
        null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
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

        Row(
            modifier = Modifier
            .padding(bottom = 6.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(0.6f)
            ) {
                TextDetails(ques = "Name", ans = liveDonationData.Member.name)
                TextDetails(ques = "Member ID", ans = liveDonationData.member_id.toString())
                TextDetails(ques = "Address", ans = "${liveDonationData.Member.district}, ${liveDonationData.Member.state}")
                TextDetails(ques = "Total donation received", ans = liveDonationData.total_donation_received)
                TextDetails(ques = "Min Amount", ans = liveDonationData?.min_amount.toString())
//                TextDetails(ques = "Date", ans = convertDateFormat(liveDonationData?.death_date.toString()).toString())
                TextDetails(ques = "Donation Start Date", ans = convertDateFormat(liveDonationData.start_date)!!)
                TextDetails(ques = "Donation End Date", ans = convertDateFormat(liveDonationData.end_date)!!)
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
                        painter = rememberAsyncImagePainter(model = "$baseUrl${liveDonationData.Member.profileUrl}")
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
            if (!sharedPref.getLoginStatus()) {
                navController.navigate("login_screen")
            } else {
                Log.d("Tag", bankDetail.toString())
                navController.navigate(
                    "make_donation_screen/${bankDetail?.bank_name ?: "Unknown Bank"}/" +
                            "${bankDetail?.ifsc_code ?: "Unknown IFSC"}/" +
                            "${bankDetail?.account_number ?: "Unknown Account"}/" +
                            "${liveDonationData.upi_id ?: "Unknown UPI"}/" +
                            "${liveDonationData.member_id ?: "Unknown Member"}/" +
                            "${liveDonationData.id ?: "Unknown ID"}"
                )
            }
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