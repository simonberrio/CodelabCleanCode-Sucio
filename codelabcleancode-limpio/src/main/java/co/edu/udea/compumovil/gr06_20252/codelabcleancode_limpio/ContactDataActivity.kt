package co.edu.udea.compumovil.gr06_20252.codelabcleancode_limpio

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr06_20252.codelabcleancode_limpio.ui.viewModels.ContactDataViewModel

class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ContactDataScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataScreen(viewModel: ContactDataViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val countries = stringArrayResource(R.array.countries)

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.contact_title)) }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormTextField(
                value = viewModel.phone.value,
                onValueChange = viewModel::onPhoneChanged,
                label = stringResource(R.string.label_phone),
                icon = Icons.Default.Call,
                error = viewModel.phoneError.value
            )

            FormTextField(
                value = viewModel.email.value,
                onValueChange = viewModel::onEmailChanged,
                label = stringResource(R.string.label_email),
                icon = Icons.Default.Email,
                error = viewModel.emailError.value
            )

            CountryDropdown(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value },
                countries = countries,
                selectedCountry = viewModel.country.value,
                onCountrySelected = viewModel::onCountryChanged,
                error = viewModel.countryError.value
            )

            FormTextField(
                value = viewModel.address.value,
                onValueChange = viewModel::onAddressChanged,
                label = stringResource(R.string.label_address),
                icon = Icons.Default.Home
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (viewModel.validateAll()) {
                        val summary = viewModel.getContactSummary()
                        Log.d(
                            "Contact Info", """
                                ${context.getString(R.string.label_phone)}: ${summary["phone"]}
                                ${context.getString(R.string.label_email)}: ${summary["email"]}
                                ${context.getString(R.string.label_address)}: ${summary["address"]}
                                ${context.getString(R.string.label_country)}: ${summary["country"]}
                                ${context.getString(R.string.label_city)}: ${summary["city"]}
                            """.trimIndent()
                        )
                        Toast.makeText(
                            context, context.getString(R.string.toast_saved), Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(context, PersonalDataActivity::class.java)
                        (context as? Activity)?.apply {
                            startActivity(intent)
                            finish()
                        }
                    }
                }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(stringResource(R.string.button_next))
            }
        }
    }
}

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    error: Int? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null
    )
    error?.let {
        Text(
            text = stringResource(it), color = Color.Red, style = MaterialTheme.typography.bodySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropdown(
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    countries: Array<String>,
    selectedCountry: String,
    onCountrySelected: (String) -> Unit,
    error: Int? = null
) {
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { onExpandedChange() }) {
        OutlinedTextField(
            readOnly = true,
            value = selectedCountry,
            onValueChange = {},
            label = { Text(stringResource(R.string.label_country)) },
            leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            isError = error != null
        )
        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { onExpandedChange() }) {
            countries.forEach { level ->
                DropdownMenuItem(
                    text = { Text(level) },
                    onClick = { onCountrySelected(level); onExpandedChange() })
            }
        }
    }
    error?.let {
        Text(
            text = stringResource(it), color = Color.Red, style = MaterialTheme.typography.bodySmall
        )
    }
}