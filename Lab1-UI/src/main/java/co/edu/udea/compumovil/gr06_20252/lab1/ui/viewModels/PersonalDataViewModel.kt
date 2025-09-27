package co.edu.udea.compumovil.gr06_20252.lab1.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.edu.udea.compumovil.gr06_20252.lab1.R

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

    fun onNameChanged(new: String) { name.value = new; nameError.value = null }
    fun onLastNameChanged(new: String) { lastName.value = new; lastNameError.value = null }
    fun onGenderChanged(new: String) { gender.value = new }
    fun onBirthdayChanged(new: String) { birthday.value = new; birthdayError.value = null }
    fun onEducationChanged(new: String) { education.value = new }

    fun validateAll(): Boolean {
        var valid = true

        if (name.value.isBlank()) {
            nameError.value = R.string.required_name
            valid = false
        }
        if (lastName.value.isBlank()) {
            lastNameError.value = R.string.required_last_name
            valid = false
        }
        if (birthday.value.isBlank()) {
            birthdayError.value = R.string.required_birthdate
            valid = false
        }

        return valid
    }
}