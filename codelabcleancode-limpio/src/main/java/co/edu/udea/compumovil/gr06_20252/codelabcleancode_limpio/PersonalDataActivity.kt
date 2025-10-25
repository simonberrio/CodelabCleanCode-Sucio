package co.edu.udea.compumovil.gr06_20252.codelabcleancode_limpio

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import co.edu.udea.compumovil.gr06_20252.codelabcleancode_limpio.ui.viewModels.PersonalDataViewModel
import java.util.Calendar

private const val TAG = "PersonalDataActivity"

class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalDataScreenClean()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreenClean(viewModel: PersonalDataViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current
    val genderOptions = listOf(
        stringResource(R.string.gender_male),
        stringResource(R.string.gender_female)
    )
    val educationLevels = stringArrayResource(R.array.education_levels)
    val expanded = remember { mutableStateOf(false) }

    // DatePicker dialog using remember to avoid recreation on recomposition
    val datePickerDialog = rememberDatePickerDialog(context) { day, month, year ->
        viewModel.onBirthdayChanged("$day/${month + 1}/$year")
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.personal_title)) }) }
    ) { padding ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)

        if (isLandscape) {
            Column(modifier = contentModifier) {
                NameRow(viewModel)
                Spacer(modifier = Modifier.height(8.dp))
                GenderRow(viewModel, genderOptions)
                Spacer(modifier = Modifier.height(8.dp))
                BirthdayRow(viewModel, datePickerDialog)
                Spacer(modifier = Modifier.height(8.dp))
                EducationAndSaveRow(
                    viewModel,
                    educationLevels,
                    expanded,
                    onSave = { handleSave(context, viewModel) },
                    onShowDatePicker = { datePickerDialog.show() }
                )
            }
        } else {
            Column(
                modifier = contentModifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NameColumn(viewModel)
                Spacer(modifier = Modifier.height(8.dp))
                GenderRow(viewModel, genderOptions)
                Spacer(modifier = Modifier.height(8.dp))
                BirthdayRow(viewModel, datePickerDialog)
                Spacer(modifier = Modifier.height(8.dp))
                EducationAndSaveColumn(
                    viewModel,
                    educationLevels,
                    expanded,
                    onSave = { handleSave(context, viewModel) },
                    onShowDatePicker = { datePickerDialog.show() }
                )
            }
        }
    }
}

@Composable
private fun NameRow(viewModel: PersonalDataViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = viewModel.name.value,
            onValueChange = viewModel::onNameChanged,
            label = { Text(stringResource(R.string.label_name)) },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.weight(1f),
            isError = viewModel.nameError.value != null,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
        )
        viewModel.nameError.value?.let { errorId ->
            Text(
                text = stringResource(errorId),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = viewModel.lastName.value,
            onValueChange = viewModel::onLastNameChanged,
            label = { Text(stringResource(R.string.label_last_name)) },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.weight(1f),
            isError = viewModel.lastNameError.value != null,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
        )
        viewModel.lastNameError.value?.let { errorId ->
            Text(
                text = stringResource(errorId),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun NameColumn(viewModel: PersonalDataViewModel) {
    OutlinedTextField(
        value = viewModel.name.value,
        onValueChange = viewModel::onNameChanged,
        label = { Text(stringResource(R.string.label_name)) },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
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
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
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
}

@Composable
private fun GenderRow(viewModel: PersonalDataViewModel, genderOptions: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(stringResource(R.string.label_gender))
        genderOptions.forEach { gender ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = viewModel.gender.value == gender, onClick = { viewModel.onGenderChanged(gender) })
                Text(gender)
            }
        }
    }
}

@Composable
private fun BirthdayRow(viewModel: PersonalDataViewModel, datePickerDialog: DatePickerDialog) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.clickable { datePickerDialog.show() }) {
            OutlinedTextField(
                value = viewModel.birthday.value,
                onValueChange = viewModel::onBirthdayChanged,
                label = { Text(stringResource(R.string.label_birthdate)) },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                readOnly = true,
                enabled = false,
                isError = viewModel.birthdayError.value != null
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { datePickerDialog.show() }) {
            Text(stringResource(R.string.button_birthdate))
        }
    }
    viewModel.birthdayError.value?.let { errorId ->
        Text(text = stringResource(errorId), color = Color.Red, style = MaterialTheme.typography.bodySmall)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EducationAndSaveRow(
    viewModel: PersonalDataViewModel,
    educationLevels: Array<String>,
    expanded: MutableState<Boolean>,
    onSave: () -> Unit,
    onShowDatePicker: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
                leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                educationLevels.forEach { level ->
                    DropdownMenuItem(text = { Text(level) }, onClick = {
                        viewModel.onEducationChanged(level)
                        expanded.value = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = onSave, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
            Text(stringResource(R.string.button_next), color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EducationAndSaveColumn(
    viewModel: PersonalDataViewModel,
    educationLevels: Array<String>,
    expanded: MutableState<Boolean>,
    onSave: () -> Unit,
    onShowDatePicker: () -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = { expanded.value = !expanded.value }) {
        OutlinedTextField(
            readOnly = true,
            value = viewModel.education.value,
            onValueChange = {},
            label = { Text(stringResource(R.string.label_education)) },
            leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            educationLevels.forEach { level ->
                DropdownMenuItem(text = { Text(level) }, onClick = {
                    viewModel.onEducationChanged(level)
                    expanded.value = false
                })
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = onSave,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(stringResource(R.string.button_next), color = MaterialTheme.colorScheme.onPrimary)
    }
}

private fun handleSave(context: android.content.Context, viewModel: PersonalDataViewModel) {
    if (!viewModel.validateAll()) return

    val summary = viewModel.getContactSummary()
    Log.d(
        "Personal Info", """
            ${context.getString(R.string.label_name)}: ${summary["name"]}
            ${context.getString(R.string.label_last_name)}: ${summary["last_name"]}
            ${context.getString(R.string.label_gender)}: ${summary["gender"]}
            ${context.getString(R.string.label_birthdate)}: ${summary["birthdate"]}
            ${context.getString(R.string.label_education)}: ${summary["education"]}
        """.trimIndent()
    )
    Toast.makeText(
        context, context.getString(R.string.toast_saved), Toast.LENGTH_SHORT
    ).show()

    val activity = context as? Activity
    context.startActivity(Intent(context, ContactDataActivity::class.java))
    activity?.finish()
}

@Composable
private fun rememberDatePickerDialog(context: android.content.Context, onDatePicked: (Int, Int, Int) -> Unit): DatePickerDialog {
    val calendar = Calendar.getInstance()
    return remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth -> onDatePicked(dayOfMonth, month, year) },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
}