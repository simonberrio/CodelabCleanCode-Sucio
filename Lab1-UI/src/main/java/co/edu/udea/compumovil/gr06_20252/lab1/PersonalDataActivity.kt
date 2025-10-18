package co.edu.udea.compumovil.gr06_20252.lab1

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr06_20252.lab1.ui.viewModels.PersonalDataViewModel
import java.util.Calendar


class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalDataScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen(viewModel: PersonalDataViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current
    val genderOptions = listOf(
        stringResource(R.string.gender_male),
        stringResource(R.string.gender_female)
    )
    val educationLevels = stringArrayResource(R.array.education_levels)
    val expanded = remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.onBirthdayChanged("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.personal_title)) })
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
                        value = viewModel.name.value,
                        onValueChange = viewModel::onNameChanged,
                        label = { Text(stringResource(R.string.label_name)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.weight(1f),
                        isError = viewModel.nameError.value != null,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                    )
                    viewModel.nameError.value?.let { errorId ->
                        Text(
                            text = stringResource(errorId),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = viewModel.lastName.value,
                        onValueChange = viewModel::onLastNameChanged,
                        label = { Text(stringResource(R.string.label_last_name)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.weight(1f),
                        isError = viewModel.lastNameError.value != null,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                    )
                    viewModel.lastNameError.value?.let { errorId ->
                        Text(
                            text = stringResource(errorId),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(stringResource(R.string.label_gender))
                    Spacer(modifier = Modifier.height(6.dp))
                    genderOptions.forEach { gender ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = viewModel.gender.value == gender,
                                onClick = { viewModel.onGenderChanged(gender) }
                            )
                            Text(gender)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { datePickerDialog.show() }
                    ) {
                        OutlinedTextField(
                            value = viewModel.birthday.value,
                            onValueChange = viewModel::onBirthdayChanged,
                            label = { Text(stringResource(R.string.label_birthdate)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null
                                )
                            },
                            readOnly = true,
                            enabled = false,
                            isError = viewModel.birthdayError.value != null
                        )
                        viewModel.birthdayError.value?.let { errorId ->
                            Text(
                                text = stringResource(errorId),
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { datePickerDialog.show() }
                    ) {
                        Text(stringResource(R.string.button_birthdate))
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = viewModel.education.value,
                            onValueChange = {},
                            label = { Text(stringResource(R.string.label_education)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            educationLevels.forEach { level ->
                                DropdownMenuItem(
                                    text = { Text(level) },
                                    onClick = {
                                        viewModel.onEducationChanged(level)
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (viewModel.validateAll()) {
                                Log.d("Información personal", "Información personal")
                                Log.d("Nombre", viewModel.name.value)
                                Log.d("Apellido", viewModel.lastName.value)
                                Log.d("Fecha de Nacimiento", viewModel.birthday.value)
                                Log.d("Género", viewModel.gender.value)
                                Log.d("Educación", viewModel.education.value)
                                Toast.makeText(
                                    context,
                                    "Datos guardados (ver Logcat)",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val activity = context as? Activity
                                val intent = Intent(context, ContactDataActivity::class.java)
                                context.startActivity(intent)
                                activity?.finish()
                            }
                        },
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
                    value = viewModel.name.value,
                    onValueChange = viewModel::onNameChanged,
                    label = { Text(stringResource(R.string.label_name)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.nameError.value != null,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
                viewModel.nameError.value?.let { errorId ->
                    Text(
                        text = stringResource(errorId),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.lastName.value,
                    onValueChange = viewModel::onLastNameChanged,
                    label = { Text(stringResource(R.string.label_last_name)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.lastNameError.value != null,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
                viewModel.lastNameError.value?.let { errorId ->
                    Text(
                        text = stringResource(errorId),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.label_gender),
                        style = MaterialTheme.typography.titleMedium
                    )
                    genderOptions.forEach { gender ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = viewModel.gender.value == gender,
                                onClick = { viewModel.onGenderChanged(gender) }
                            )
                            Text(gender)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = viewModel.birthday.value,
                        onValueChange = viewModel::onBirthdayChanged,
                        label = { Text(stringResource(R.string.label_birthdate)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        },
                        readOnly = true,
                        enabled = false,
                        isError = viewModel.birthdayError.value != null,
                        modifier = Modifier.weight(1f) // ocupa todo el espacio disponible
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { datePickerDialog.show() }
                    ) {
                        Text(stringResource(R.string.button_birthdate))
                    }
                }
                viewModel.birthdayError.value?.let { errorId ->
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
                        value = viewModel.education.value,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.label_education)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        educationLevels.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level) },
                                onClick = {
                                    viewModel.onEducationChanged(level)
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (viewModel.validateAll()) {
                            Log.d("Información personal", "Información personal")
                            Log.d("Nombre", viewModel.name.value)
                            Log.d("Apellido", viewModel.lastName.value)
                            Log.d("Fecha de Nacimiento", viewModel.birthday.value)
                            Log.d("Género", viewModel.gender.value)
                            Log.d("Educación", viewModel.education.value)
                            Toast.makeText(
                                context,
                                "Datos guardados (ver Logcat)",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val activity = context as? Activity
                            val intent = Intent(context, ContactDataActivity::class.java)
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