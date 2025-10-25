package co.edu.udea.compumovil.gr06_20252.cleancode.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import co.edu.udea.compumovil.gr06_20252.cleancode.R

class DataViewModel: ViewModel() {
    var n = mutableStateOf("")
    var nErr = mutableStateOf<Int?>(null)
    var ln = mutableStateOf("")
    var lnErr = mutableStateOf<Int?>(null)
    var g = mutableStateOf("")
    var b = mutableStateOf("")
    var bErr = mutableStateOf<Int?>(null)
    var edu = mutableStateOf("")

    fun c1(s: String){ n.value = s }
    fun c2(s: String){ ln.value = s }
    fun c3(s: String){ g.value = s }
    fun c4(s: String){ b.value = s }
    fun c5(s: String){ edu.value = s }

    fun check(): Boolean {
        var ok = true
        if (n.value == ""){
            nErr.value = R.string.required_name
            ok = false
        } else {
            nErr.value = null
        }

        if (ln.value == ""){
            lnErr.value = R.string.required_last_name
            ok = false
        } else {
            lnErr.value = null
        }

        if (b.value == ""){
            bErr.value = R.string.required_birthdate
            ok = false
        } else {
            bErr.value = null
        }

        println("checking fields... ok? $ok")

        return ok
    }
}