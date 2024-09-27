package app.mtt.aggrabandhu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.mtt.aggrabandhu.ui.theme.AggrabandhuTheme
import app.mtt.aggrabandhu.utils.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {

            val sp = SharedPrefManager(this)
            val uId = sp.getLoginStatus()

            val loginStatus = if(uId?.isEmpty()!!) false else true
            NavigationComponent(loginStatus)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AggrabandhuTheme {
        //LoginScreen()
    }
}