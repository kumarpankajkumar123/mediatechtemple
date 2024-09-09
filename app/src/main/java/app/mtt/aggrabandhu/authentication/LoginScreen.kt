package app.mtt.aggrabandhu.authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.utils.CircularImage
import app.mtt.aggrabandhu.utils.CustomButton
import app.mtt.aggrabandhu.utils.PasswordTextFieldWithIcons
import app.mtt.aggrabandhu.utils.TextFieldWithIcons

@Composable
fun LoginScreen (navController: NavController) {

    val context = LocalContext.current
    var username : String ?= ""
    var password : String ?= ""

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // below is the composable for image.
        CircularImage(width = 300.dp, painter = painterResource(id = R.drawable.ic_launcher_foreground))

        Spacer(modifier = Modifier.height(10.dp))

        // Heading Login
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.login_to_continue),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        /* ------------------- Login Username ----------------------- */
        TextFieldWithIcons( "Username","Enter your Username", Icons.Filled.Person) {
            username = it
        }

        /* ------------------- Password ----------------------- */
        PasswordTextFieldWithIcons {
            password = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        /* ---------- Login Button -------------- */
        CustomButton("Login", colorResource(id = R.color.black)) {
            if (username?.length!! < 3) {
                Toast.makeText(context, "Please enter username", Toast.LENGTH_SHORT).show()
            } else if (password?.isEmpty()!!) {
                Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "$username - $password", Toast.LENGTH_SHORT).show()
            }
        }

        Text(
            text = "Forget Password?",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.End,
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Box(modifier = Modifier.fillMaxSize(0.9f), contentAlignment = Alignment.BottomCenter) {
            CustomButton(text = "New User? Signup now", colorResource(id = R.color.sky_blue)) {
                navController.navigate("signup_screen")
            }
        }

    }
}