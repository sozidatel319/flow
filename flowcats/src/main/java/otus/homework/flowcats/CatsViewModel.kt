package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val catsMutableStateFlow =
        MutableStateFlow(
            Fact(
                "",
                false,
                "",
                "",
                "",
                false,
                "",
                "",
                ""
            )
        )
    val catsStateFlow: StateFlow<Fact> = catsMutableStateFlow.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(200),
        initialValue = Fact(
            "",
            false,
            "",
            "",
            "",
            false,
            "",
            "",
            ""
        )
    )

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                catsRepository.listenForCatFacts().collect {
                    if (it is ApiResult.ApiSuccess){
                        catsMutableStateFlow.value = it.data
                    }
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
