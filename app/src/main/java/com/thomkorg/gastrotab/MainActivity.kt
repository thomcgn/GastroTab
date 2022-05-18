package com.thomkorg.gastrotab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thomkorg.gastrotab.ui.theme.GastroTabTheme

class MainActivity : ComponentActivity() {

    val tischCollectionRef = Firebase.firestore.collection("tische")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            GastroTabTheme {
                LoginScreen()
            }
        }
    }


}

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(id = R.drawable.ic_cropped_logo_sw_removebg_preview),
                contentDescription = ""
            )
            Column(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 48.dp,
                    end = 8.dp,
                    bottom = 16.dp
                )
            ) {
                UsernameTextField()
            }
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PasswordTextField()
            }

            Column(
                modifier = Modifier
                    .padding(start = 48.dp, end = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(modifier = Modifier
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Anmelden")

                }
            }
        }

    }


}

@Preview
@Composable
fun PreviewScreen() {
    LoginScreen()
}

@Composable
fun UsernameTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        label = { Text(text = "Benutzername") },
        onValueChange = {
            text = it
        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        placeholder = { Text(text = "Mitarbeiter Email") },
        leadingIcon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = null) }
    )
}

@Composable
fun PasswordTextField() {
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable() { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Passwort") },
        singleLine = true,
        placeholder = { Text(text = "Passwort") },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Passwort ausblenden" else "Passwort anzeigen"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)

            }
        }
    )

}
