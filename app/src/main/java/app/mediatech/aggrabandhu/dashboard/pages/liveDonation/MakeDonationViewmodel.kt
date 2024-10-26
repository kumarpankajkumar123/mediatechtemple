package app.mediatech.aggrabandhu.dashboard.pages.liveDonation

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MakeDonationViewmodel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

        val responseCode : StateFlow<Int>
            get() = repository.makeDonationResponse

        private val id : String
            get() = savedStateHandle.get<String>("id")!!
        val bankName : String
            get() = savedStateHandle.get<String>("bankName")!!
        val ifsc : String
            get() = savedStateHandle.get<String>("ifsc")!!
        val upiId : String
            get() = savedStateHandle.get<String>("upiId")!!
        val accountNumb : String
            get() = savedStateHandle.get<String>("accountNumb")!!

    var method by mutableStateOf("")
    var amount by mutableStateOf("")
    var transactionNumb by mutableStateOf("")
    var transactionDate by mutableStateOf("")
    var uri by mutableStateOf<Uri?>(null)
    var file by mutableStateOf<MultipartBody.Part?>(null)

        fun makeDonation(memberId:String, context: Context) {
            viewModelScope.launch {
                repository.makeDonation(
                    memberId,
                    id,
                    amount,
                    file!!,
                    transactionNumb,
                    method,
                    transactionDate,
                    context
                )
            }
        }

}