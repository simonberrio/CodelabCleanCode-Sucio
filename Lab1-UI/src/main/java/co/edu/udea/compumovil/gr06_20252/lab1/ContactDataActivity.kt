package co.edu.udea.compumovil.gr06_20252.lab1

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import co.edu.udea.compumovil.gr06_20252.lab1.ui.viewModels.ContactDataViewModel

class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactDataScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataScreen(viewModel: ContactDataViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current

    val cityExpanded = remember { mutableStateOf(false) }
    val countries = stringArrayResource(R.array.countries)
    val expanded = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.contact_title)) })
        }
    ) { padding ->
        if (isLandscape) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(4.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.phone.value,
                        onValueChange = viewModel::onPhoneChanged,
                        label = { Text(stringResource(R.string.label_phone)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.weight(1f),
                        isError = viewModel.phoneError.value != null
                    )
                    viewModel.phoneError.value?.let { errorId ->
                        Text(
                            text = stringResource(errorId),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = viewModel.email.value,
                        onValueChange = viewModel::onEmailChanged,
                        label = { Text(stringResource(R.string.label_email)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.weight(1f),
                        isError = viewModel.emailError.value != null
                    )
                    viewModel.emailError.value?.let { errorId ->
                        Text(
                            text = stringResource(errorId),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = viewModel.country.value,
                            onValueChange = {},
                            label = { Text(stringResource(R.string.label_country)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            isError = viewModel.countryError.value != null
                        )
                        ExposedDropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            countries.forEach { level ->
                                DropdownMenuItem(
                                    text = { Text(level) },
                                    onClick = {
                                        viewModel.onCountryChanged(level)
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }
                    viewModel.countryError.value?.let { errorId ->
                        Text(
                            text = stringResource(errorId),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = viewModel.address.value,
                        onValueChange = viewModel::onAddressChanged,
                        label = { Text(stringResource(R.string.label_address)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
//                    ExposedDropdownMenuBox(
//                        expanded = cityExpanded.value,
//                        onExpandedChange = { cityExpanded.value = !cityExpanded.value },
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        OutlinedTextField(
//                            value = viewModel.city.value,
//                            onValueChange = {
//                                viewModel.city.value = it
//                                viewModel.searchCities(it)
//                                cityExpanded.value = true
//                            },
//                            label = { Text("Ciudad") },
//                            leadingIcon = {
//                                Icon(
//                                    Icons.Default.Place,
//                                    contentDescription = null
//                                )
//                            },
//                            trailingIcon = {
//                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityExpanded.value)
//                            },
//                            modifier = Modifier.menuAnchor()
//                        )
//                        ExposedDropdownMenu(
//                            expanded = cityExpanded.value,
//                            onDismissRequest = { cityExpanded.value = false }
//                        ) {
//                            viewModel.cities.value.forEach { city ->
//                                DropdownMenuItem(
//                                    text = { Text(city) },
//                                    onClick = {
//                                        viewModel.city.value = city
//                                        cityExpanded.value = false
//                                    }
//                                )
//                            }
//                        }
//                    }

                    Button(
                        onClick = {
                            if (viewModel.validateAll()) {
                                Log.d("Información de contacto", "Información de contacto")
                                Log.d("Teléfono", viewModel.phone.value)
                                Log.d("Correo", viewModel.email.value)
                                Log.d("País", viewModel.country.value)
                                Log.d("Dirección", viewModel.address.value)
                                Toast.makeText(
                                    context,
                                    "Datos guardados (ver Logcat)",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val activity = context as? Activity
                                val intent = Intent(context, PersonalDataActivity::class.java)
                                context.startActivity(intent)
                                activity?.finish()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(stringResource(R.string.button_next))
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = viewModel.phone.value,
                    onValueChange = viewModel::onPhoneChanged,
                    label = { Text(stringResource(R.string.label_phone)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.phoneError.value != null
                )
                viewModel.phoneError.value?.let { errorId ->
                    Text(
                        text = stringResource(errorId),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.email.value,
                    onValueChange = viewModel::onEmailChanged,
                    label = { Text(stringResource(R.string.label_email)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.emailError.value != null
                )
                viewModel.emailError.value?.let { errorId ->
                    Text(
                        text = stringResource(errorId),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = !expanded.value }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = viewModel.country.value,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.label_country)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        isError = viewModel.countryError.value != null
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        countries.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level) },
                                onClick = {
                                    viewModel.onCountryChanged(level)
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
                viewModel.countryError.value?.let { errorId ->
                    Text(
                        text = stringResource(errorId),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.address.value,
                    onValueChange = viewModel::onAddressChanged,
                    label = { Text(stringResource(R.string.label_address)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = cityExpanded.value,
                    onExpandedChange = { cityExpanded.value = !cityExpanded.value },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = viewModel.city.value,
                        onValueChange = {
                            viewModel.city.value = it
                            viewModel.searchCities(it)
                            cityExpanded.value = true
                        },
                        label = { Text("Ciudad") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Place,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityExpanded.value)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = cityExpanded.value,
                        onDismissRequest = { cityExpanded.value = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Log.d("viewModel.cities", viewModel.cities.toString())
                        viewModel.cities.value.forEach { city ->
                            DropdownMenuItem(
                                text = { Text(city) },
                                onClick = {
                                    viewModel.city.value = city
                                    cityExpanded.value = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (viewModel.validateAll()) {
                            Log.d("Información de contacto", "Información de contacto")
                            Log.d("Teléfono", viewModel.phone.value)
                            Log.d("Correo", viewModel.email.value)
                            Log.d("País", viewModel.country.value)
                            Log.d("Dirección", viewModel.address.value)
                            Toast.makeText(
                                context,
                                "Datos guardados (ver Logcat)",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val activity = context as? Activity
                            val intent = Intent(context, PersonalDataActivity::class.java)
                            context.startActivity(intent)
                            activity?.finish()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(stringResource(R.string.button_next))
                }
            }
        }
    }
}