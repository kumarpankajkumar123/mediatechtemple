package app.mediatech.aggrabandhu.dashboard.pages.rules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mediatech.aggrabandhu.utils.HtmlText

@Composable
fun RulesRegulationsPage(modifier: Modifier = Modifier) {

    val rulesViewModel : RulesAndRegulationsViewmodel = hiltViewModel()
    val rules = rulesViewModel.rules.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Rules and Regulations",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(top=16.dp, start = 8.dp)
        )
        HtmlText(html = rules.value, modifier = Modifier.padding(8.dp))
    }
}