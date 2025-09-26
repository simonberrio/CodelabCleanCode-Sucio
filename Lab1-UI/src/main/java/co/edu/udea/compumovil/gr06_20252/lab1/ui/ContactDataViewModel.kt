package co.edu.udea.compumovil.gr06_20252.lab1.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ContactDataViewModel : ViewModel() {
    var phone = mutableStateOf("")
        private set
    var email = mutableStateOf("")
        private set
    var address = mutableStateOf("")
        private set

    fun onPhoneChanged(value: String) { phone.value = value }

    fun onEmailChanged(value: String) { email.value = value }

    fun onAddressChanged(value: String) { address.value = value }
}