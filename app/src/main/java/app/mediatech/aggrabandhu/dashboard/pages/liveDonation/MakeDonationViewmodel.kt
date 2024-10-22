package app.mediatech.aggrabandhu.dashboard.pages.liveDonation

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MakeDonationViewmodel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

        val bankName : String
            get() = savedStateHandle.get<String>("bankName")!!
        val ifsc : String
            get() = savedStateHandle.get<String>("ifsc")!!
        val accountNumb : String
            get() = savedStateHandle.get<String>("accountNumb")!!

    var transactionNumb by mutableStateOf("")
    var uri by mutableStateOf<Uri?>(null)



}