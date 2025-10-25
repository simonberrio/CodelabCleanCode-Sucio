package co.edu.udea.compumovil.gr06_20252.cleancode

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import java.util.Calendar

class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalDataScreenSucia()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreenSucia() {
    // TODO: aquí no usamos ViewModel — todo el estado está en el Composable (mala práctica)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current

    // Nombres mal nombrados
    var n by remember { mutableStateOf("") }
    var ln by remember { mutableStateOf("") }
    var bd by remember { mutableStateOf("") }
    var gen by remember { mutableStateOf("M") }
    var edu by remember { mutableStateOf("Primaria") }

    // errores duplicados e implementados inline
    var nErr by remember { mutableStateOf<String?>(null) }
    var lnErr by remember { mutableStateOf<String?>(null) }

    val cal = Calendar.getInstance()
    val dp = DatePickerDialog(
        context,
        { _, y, m, d ->
            bd = "$d/${m + 1}/$y"
        },
cal.get(Calendar.YEAR),
cal.get(Calendar.MONTH),
cal.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(topBar = { TopAppBar(title = { Text("Datos personales") }) }) { padding ->
        if (isLandscape) {
            Column(
                modifier = Modifier
                        .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
            ) {
                Row {
                    OutlinedTextField(
                        value = n,
                        onValueChange = {
                            n = it
                            if (it.length < 3) nErr = "Nombre muy corto" else nErr = null
                        },
                        label = { Text("Nombre") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.weight(1f),
                        isError = nErr != null,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                    )
                    if (nErr != null) Text(nErr!!, color = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = ln,
                        onValueChange = {
                            ln = it
                            if (it.isEmpty()) lnErr = "Ingrese apellido" else lnErr = null
                        },
                        label = { Text("Apellido") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier.weight(1f),
                            isError = lnErr != null,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                    )
                    if (lnErr != null) Text(lnErr!!, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Género hardcodeado (sin manejo de strings.resource) y sin accesibilidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Género:")
                    RadioButton(selected = gen == "M", onClick = { gen = "M" })
                Text(text = "M")
                RadioButton(selected = gen == "F", onClick = { gen = "F" })
                    Text(text = "F")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    OutlinedTextField(
                        value = bd,
                        onValueChange = { bd = it },
                        label = { Text("Nacimiento") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = { dp.show() }) {
                        Text("Elegir fecha")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown manual y sucio
                OutlinedTextField(value = edu, onValueChange = { edu = it }, label = { Text("Educación") })

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    // Validaciones duplicadas y logs sin TAG constante
                    if (n.length < 3) {
                        Log.d("Personal", "Nombre invalido")
                        Toast.makeText(context, "No guardado - nombre", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (ln.isEmpty()) {
                        Log.d("Personal", "Apellido invalido")
                        Toast.makeText(context, "No guardado - apellido", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Logs repetitivos
                    Log.d("INFO", "N: $n")
                    Log.d("INFO", "LN: $ln")
                    Log.d("INFO", "BD: $bd")
                    Log.d("INFO", "GEN: $gen")
                    Log.d("INFO", "EDU: $edu")

                    Toast.makeText(context, "Guardado! (revisar Logcat)", Toast.LENGTH_SHORT).show()
                    val activity = context as? Activity
                    context.startActivity(Intent(context, ContactDataActivity::class.java))
                    activity?.finish()
                }) {
                    Text("Guardar")
                }
            }
        } else {
            // Versión móvil similar pero repetida (duplicación deliberada)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = n,
                    onValueChange = { n = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = ln,
                    onValueChange = { ln = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    // Validación duplicada: la misma lógica que arriba, pero aquí repetida
                    if (n.length < 3) {
                        //Mensaje de erro enviado por log, en lugar de mostrarle al usuario
                        Log.d("Personal", "Nombre invalido")
                        Toast.makeText(context, "No guardado - nombre", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (ln.isEmpty()) {
                        Log.d("Personal", "Apellido invalido")
                        Toast.makeText(context, "No guardado - apellido", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    Log.d("Personal", "Guardado: $n $ln")
                    Toast.makeText(context, "Guardado! (revisar Logcat)", Toast.LENGTH_SHORT).show()
                    context.startActivity(Intent(context, ContactDataActivity::class.java))
                }) {
                    Text("Guardar")
                }
            }
        }
    }
}