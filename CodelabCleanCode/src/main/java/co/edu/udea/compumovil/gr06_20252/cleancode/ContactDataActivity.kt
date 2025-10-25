package co.edu.udea.compumovil.gr06_20252.cleancode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.edu.udea.compumovil.gr06_20252.cleancode.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr06_20252.cleancode.PersonalDataActivity

class ContactDataActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ctx = LocalContext.current
            var phone = remember { mutableStateOf("") }
            var mail = remember { mutableStateOf("") }
                var country = remember { mutableStateOf("") }
                var address = remember { mutableStateOf("") }
            var city = remember { mutableStateOf("") }

        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Contact info form", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(
                    value = phone.value,
                    onValueChange = { phone.value = it },
                    label = { Text("Phone number") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = mail.value,
                    onValueChange = { mail.value = it },
                    label = { Text("Email address") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = country.value,
                    onValueChange = { country.value = it },
                    label = { Text("Country") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = city.value,
                    onValueChange = { city.value = it },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (phone.value == "" || mail.value == "" || address.value == "") {
                            Toast.makeText(ctx, "Fields missing!", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("Contact info", "Phone: " + phone.value)
                        Log.d("Contact info", "Email: " + mail.value)
                        Log.d("Contact info", "Country: " + country.value)
                        Log.d("Contact info", "Address: " + address.value)
                            Log.d("Contact info", "City: " + city.value)
                            Toast.makeText(ctx, "Saved!", Toast.LENGTH_SHORT).show()

                            val i = Intent(ctx, PersonalDataActivity::class.java)
                            ctx.startActivity(i)
                            (ctx as? Activity)?.finish()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save and continue")
                }

                // código duplicado para landscape (sin necesidad real)
                Spacer(modifier = Modifier.height(20.dp))
                Text("---------- Landscape Layout Below ----------", color = Color.Gray)

                Column {
                    Text("Phone: ${phone.value}")
                    Text("Email: ${mail.value}")
                    Text("Country: ${country.value}")
                    Text("Address: ${address.value}")
                    Text("City: ${city.value}")
                }
            }
        }
    }
}