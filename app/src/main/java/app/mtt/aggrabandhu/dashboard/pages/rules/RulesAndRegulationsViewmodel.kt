package app.mtt.aggrabandhu.dashboard.pages.rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulesAndRegulationsViewmodel @Inject constructor(
    private val repository: Repository) : ViewModel() {

    val rules : StateFlow<String>
        get() = repository.rules
    val declaration : StateFlow<String>
        get() = repository.declaration

    init {
        viewModelScope.launch {
//            repository.getDeclaration()
            repository.getRules()
        }
    }
}