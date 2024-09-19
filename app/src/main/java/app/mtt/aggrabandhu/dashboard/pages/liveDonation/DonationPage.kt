package app.mtt.aggrabandhu.dashboard.pages.liveDonation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mtt.aggrabandhu.dashboard.sideNavigation.DonorsData
import app.mtt.aggrabandhu.dashboard.sideNavigation.TextDetails
import app.mtt.aggrabandhu.utils.CircularImage
import app.mtt.aggrabandhu.utils.CustomButton2

@Preview
@Composable
fun DonationsPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "All Live Donations",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        LiveDonations()
        LiveDonations()
        LiveDonations()
    }
}

@Preview
@Composable
private fun LiveDonations (
    liveDonationData: LiveDonationData ?= null
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
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            Text(
                text = "Asharam Babu",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 2.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

        }

        Row(modifier = Modifier
            .padding(bottom = 6.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(0.6f)
            ) {
                TextDetails(ques = "Name", ans = "Asharam Babu")
                TextDetails(ques = "Member ID", ans = "1234")
                TextDetails(ques = "Address", ans = "Jodhpur, Rajasthan")
                TextDetails(ques = "Bank", ans = "Dena Bank")
                TextDetails(ques = "Start Date", ans = "33/33/90")
                TextDetails(ques = "End Date", ans = "33/33/90")
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
                            text = "Active",
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

    }
}
