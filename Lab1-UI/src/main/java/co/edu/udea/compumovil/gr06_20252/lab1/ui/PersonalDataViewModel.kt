package co.edu.udea.compumovil.gr06_20252.lab1.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PersonalDataViewModel : ViewModel() {
    var name = mutableStateOf("")
        private set
    var lastName = mutableStateOf("")
        private set
    var birthday = mutableStateOf("")
        private set
    var gender = mutableStateOf("")
        private set
    var education = mutableStateOf("")
        private set

    fun onNameChanged(new: String) { name.value = new }
    fun onLastNameChanged(new: String) { lastName.value = new }
    fun onBirthdayChanged(new: String) { birthday.value = new }
    fun onGenderChanged(new: String) { gender.value = new }
    fun onEducationChanged(new: String) { education.value = new }
}