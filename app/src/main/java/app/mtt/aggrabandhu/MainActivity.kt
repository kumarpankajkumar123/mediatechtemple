package app.mtt.aggrabandhu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.mtt.aggrabandhu.ui.theme.AggrabandhuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            NavigationComponent()
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