package app.mediatech.aggrabandhu.dashboard.pages.liveDonation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.compressImageToUri
import app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations.MyDonationViewmodel
import app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations.TextDetails
import app.mediatech.aggrabandhu.utils.CustomButton
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SelectImageCardWithButton
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons
import app.mediatech.aggrabandhu.utils.prepareFilePart
import es.dmoral.toasty.Toasty

data class BankDetails(
    val bankName : String,
    val accountNumber : String,
    val ifscCode : String,
    val upiID: String,
    val personNameOnUPI : String
)

@Preview
@Composable
fun MakeDonationPage(
    navController: NavController ?= null
) {
    val context = LocalContext.current

    val makeDonationViewmodel : MakeDonationViewmodel = hiltViewModel()

    val sp = SharedPrefManager(context)

    val mId = sp.getMemberID()

    val showProgress = remember { mutableStateOf(false) }

    val responseCode = makeDonationViewmodel.responseCode.collectAsState()

    if (responseCode.value != 0) {
        showProgress.value = false
        if (responseCode.value == 200) {
            makeDonationViewmodel.amount = ""
            makeDonationViewmodel.transactionNumb = ""
            makeDonationViewmodel.method = ""
            navController?.popBackStack()
        }
    }

    if (showProgress.value) {
        LoadingAlertDialog()
    }

    val bankDetails = BankDetails(
        makeDonationViewmodel.bankName,
        makeDonationViewmodel.accountNumb,
        makeDonationViewmodel.ifsc,
        makeDonationViewmodel.upiId,
        "Ram Babu")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navController?.popBackStack() }
                    .size(28.dp)
            )
            Text(
                text = "Make Donation",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Text(
            text = "Please make donation to the given details",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
            )

        TransactionMedium(
            Icons.Default.AccountBalance,
            bankDetails,
            context
        )
        TransactionMediumUPI(
            Icons.Default.Payments,
            bankDetails
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 15.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.Payment,
                contentDescription = "Back",
                modifier = Modifier
            )
            Text(
                text = "Payment Details",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        Text(
            text = "After making donation Please provide us the transaction Details",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 5.dp)
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ){
            TextFieldWithIcons(
                label = "Donated Amount",
                placeholder = "Donated Amount",
                maxLength = 7,
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.CurrencyRupee
            ) {
                makeDonationViewmodel.amount = it
            }

            TextFieldWithIcons(
                label = "Payment Method",
                placeholder = "Payment Method",
                maxLength = 27,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Payment
            ) {
                makeDonationViewmodel.method = it
            }

            TextFieldWithIcons(
                label = "Transaction ID",
                placeholder = "Transaction ID",
                maxLength = 30,
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.Payment
            ) {
                makeDonationViewmodel.transactionNumb = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            SelectImageCardWithButton(docType = "Payment ScreenShot") {
                val uri = compressImageToUri(it, context)
                makeDonationViewmodel.uri = uri
                makeDonationViewmodel.file = prepareFilePart(uri!!, "file", context)
            }
            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(text = "Submit", background = colorResource(id = R.color.orange)) {
                showProgress.value = true
                makeDonationViewmodel.makeDonation(mId.toString(), context)
            }

        }
    }

}

@Composable
fun TransactionMedium(
    imageVector: ImageVector,
    bankDetails: BankDetails,
    context: Context
) {

    Surface(
        shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 6.dp)
            .clickable {
                val clipboard: ClipboardManager? =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText("label", bankDetails.accountNumber)
                clipboard?.setPrimaryClip(clip)
                Toasty
                    .success(context, "Account No. Copied")
                    .show()
            },
        shadowElevation = 4.dp,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
                Column (
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    TextDetails(ques = "Bank account number", ans = bankDetails.accountNumber)
                    TextDetails(ques = "Bank Name", ans = bankDetails.bankName)
                    TextDetails(ques = "IFSC Code", ans = bankDetails.ifscCode)
                }
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(imageVector = Icons.Default.CopyAll, contentDescription = "")
                }
            }
        }
    }
}

@Composable
fun TransactionMediumUPI(
    imageVector: ImageVector,
    bankDetails: BankDetails
) {
    val context = LocalContext.current
    Surface(
        shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 6.dp)
            .clickable {
                val clipboard: ClipboardManager? =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText("Upi ID", bankDetails.upiID)
                clipboard?.setPrimaryClip(clip)
                Toasty
                    .success(context, "UPI ID Copied : ${bankDetails.upiID}")
                    .show()
            },
        shadowElevation = 4.dp,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                    TextDetails(ques = "UPI ID", ans = bankDetails.upiID)
                    TextDetails(ques = "Name", ans = bankDetails.personNameOnUPI)
                }

                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(imageVector = Icons.Default.CopyAll, contentDescription = "")
                }
            }
        }
    }
}