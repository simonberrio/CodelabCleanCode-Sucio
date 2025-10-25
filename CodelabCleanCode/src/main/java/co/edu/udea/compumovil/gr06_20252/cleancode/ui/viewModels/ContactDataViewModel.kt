package co.edu.udea.compumovil.gr06_20252.cleancode.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import android.util.Patterns
import co.edu.udea.compumovil.gr06_20252.cleancode.R

class CViewModel : ViewModel() {

    var p = mutableStateOf("")
    var pe = mutableStateOf<Int?>(null)
    var e = mutableStateOf("")
    var ee = mutableStateOf<Int?>(null)
    var a = mutableStateOf("")
    var ctry = mutableStateOf("")
    var ctryErr = mutableStateOf<Int?>(null)
    var cty = mutableStateOf("")
    var ctyErr = mutableStateOf<Int?>(null)
    var cs = mutableStateOf(listOf<String>())

    fun ch1(v: String){ p.value = v }
    fun ch2(v: String){ e.value = v }
    fun ch3(v: String){ a.value = v }
    fun ch4(v: String){ ctry.value = v }
    fun ch5(v: String){ cty.value = v }

    fun validate(): Boolean {
        var ok = true
        if (p.value == "") {
            pe.value = R.string.required_phone
            ok = false
        } else {
            pe.value = null
        }

        if (e.value == "") {
            ee.value = R.string.required_email
            ok = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(e.value).matches()) {
            ee.value = R.string.invalid_email
            ok = false
        } else {
            ee.value = null
        }

        if (ctry.value == "") {
            ctryErr.value = R.string.required_country
            ok = false
        } else {
            ctryErr.value = null
        }

        println("validation finished: $ok")
        return ok
    }
}