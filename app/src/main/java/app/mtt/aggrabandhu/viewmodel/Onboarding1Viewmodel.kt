package app.mtt.aggrabandhu.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Onboarding1Viewmodel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val profession : StateFlow<List<ProfessionData>>
        get() = repository.professions

    val gotra : StateFlow<List<ProfessionData>>
        get() = repository.gotra

    val getName : String
        get() = savedStateHandle.get<String>("name")!!

    init {
        viewModelScope.launch {
            repository.getProfession()
            repository.getGotra()
        }
    }

}