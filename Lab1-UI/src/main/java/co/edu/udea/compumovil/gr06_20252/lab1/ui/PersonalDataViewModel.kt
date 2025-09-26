package co.edu.udea.compumovil.gr06_20252.lab1.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PersonalFormState(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "", // "M", "F", ""
    val birthDateMillis: Long? = null,
    val education: String = ""
)

class PersonalDataViewModel(private val state: SavedStateHandle) : ViewModel() {
    companion object {
        private const val KEY_STATE = "personal_state"
    }

    private val _uiState = MutableStateFlow(
        state.get<PersonalFormState>(KEY_STATE) ?: PersonalFormState()
    )
    val uiState: StateFlow<PersonalFormState> = _uiState.asStateFlow()

    fun updateFirstName(value: String) { update { it.copy(firstName = value) } }
    fun updateLastName(value: String) { update { it.copy(lastName = value) } }
    fun updateGender(value: String) { update { it.copy(gender = value) } }

    fun updateBirthDate(millis: Long?) { update { it.copy(birthDateMillis = millis) } }
    fun updateEducation(value: String) { update { it.copy(education = value) } }

    private fun update(transform: (PersonalFormState) -> PersonalFormState) {
        val newState = transform(_uiState.value)
        _uiState.value = newState
        state[KEY_STATE] = newState
    }
}