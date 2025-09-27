package co.edu.udea.compumovil.gr06_20252.lab1.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.udea.compumovil.gr06_20252.lab1.R
import co.edu.udea.compumovil.gr06_20252.lab1.data.interfaces.RetrofitInstance
import kotlinx.coroutines.launch
import kotlin.text.isBlank

class ContactDataViewModel : ViewModel() {
    var phone = mutableStateOf("")
        private set
    var phoneError = mutableStateOf<Int?>(null)
        private set
    var email = mutableStateOf("")
        private set
    var emailError = mutableStateOf<Int?>(null)
        private set
    var address = mutableStateOf("")
        private set

    var country = mutableStateOf("")
        private set
    var countryError = mutableStateOf<Int?>(null)
        private set

    var city = mutableStateOf("")
        private set
    var cityError = mutableStateOf<Int?>(null)
        private set

    var cities = mutableStateOf<List<String>>(emptyList())
        private set

    fun onPhoneChanged(value: String) { phone.value = value; phoneError.value = null }

    fun onEmailChanged(value: String) { email.value = value; emailError.value = null }

    fun onAddressChanged(value: String) { address.value = value }

    fun onCountryChanged(value: String) { country.value = value; countryError.value = null }

    fun onCityChanged(value: String) { city.value = value }

    fun searchCities(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCities(
                    query,
                    country = "CO",
                    maxRows = 10,
                    username = "simon.berrio"
                )
                cities.value = response.geonames.map { it.name }
            } catch (e: Exception) {
                cities.value = emptyList()
            }
        }
    }

    fun validateAll(): Boolean {
        var valid = true

        if (phone.value.isBlank()) {
            phoneError.value = R.string.required_phone
            valid = false
        }
        if (email.value.isBlank()) {
            emailError.value= R.string.required_email
            valid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            emailError.value = R.string.invalid_email
            valid = false
        }
        if (country.value.isBlank()) {
            countryError.value = R.string.required_country
            valid = false
        }

        return valid
    }
}