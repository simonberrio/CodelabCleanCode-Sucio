package co.edu.udea.compumovil.gr06_20252.codelabcleancode_limpio.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.edu.udea.compumovil.gr06_20252.codelabcleancode_limpio.R

/**
 * ViewModel encargado de manejar el estado y la validación
 * de los datos personales del formulario.
 */
class PersonalDataViewModel : ViewModel() {

    var name = mutableStateOf("")
        private set
    var nameError = mutableStateOf<Int?>(null)
        private set

    var lastName = mutableStateOf("")
        private set
    var lastNameError = mutableStateOf<Int?>(null)
        private set

    var gender = mutableStateOf("")
        private set

    var birthday = mutableStateOf("")
        private set
    var birthdayError = mutableStateOf<Int?>(null)
        private set

    var education = mutableStateOf("")
        private set

    // --- Updates ---
    fun onNameChanged(new: String) {
        name.value = new
        nameError.value = null
    }

    fun onLastNameChanged(new: String) {
        lastName.value = new
        lastNameError.value = null
    }

    fun onGenderChanged(new: String) {
        gender.value = new
    }

    fun onBirthdayChanged(new: String) {
        birthday.value = new
        birthdayError.value = null
    }

    fun onEducationChanged(new: String) {
        education.value = new
    }

    fun getContactSummary(): Map<String, String> {
        return mapOf(
            "name" to name.value,
            "lastName" to lastName.value,
            "gender" to gender.value,
            "birthday" to birthday.value,
            "education" to education.value
        )
    }

    // --- Validation ---
    fun validateAll(): Boolean {
        var isValid = true

        if (name.value.isBlank()) {
            nameError.value = R.string.required_name
            isValid = false
        }

        if (lastName.value.isBlank()) {
            lastNameError.value = R.string.required_last_name
            isValid = false
        }

        if (birthday.value.isBlank()) {
            birthdayError.value = R.string.required_birthdate
            isValid = false
        }

        return isValid
    }
}