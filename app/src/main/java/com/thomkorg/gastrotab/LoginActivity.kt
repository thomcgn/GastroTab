package com.thomkorg.gastrotab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.VERBOSE
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thomkorg.gastrotab.ui.theme.GastroTabTheme
import com.thomkorg.gastrotab.viewmodels.LoginViewModel

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    val model by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        auth.signOut()
        super.onCreate(savedInstanceState)

        setContent {
            GastroTabTheme {
                // A surface container using the 'background' color from the theme

                PreviewScreen()

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
                    UsernameTextField(model)
                }
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PasswordTextField(model)
                }

                Column(
                    modifier = Modifier
                        .padding(start = 48.dp, end = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        onClick = { switchToMain() },
                        enabled = model.userName.isNotBlank() && model.userPassword.isNotBlank()
                    )
                    {
                        Text(text = "Anmelden")

                    }
                    Text(color = Color.White, text = "Noch kein Konto?")
                }
            }

        }


    }

    @Composable
    fun UsernameTextField(model: LoginViewModel) {

        TextField(
            value = model.userName,
            label = { Text(text = "Benutzername") },
            onValueChange = {
                model.onUserNameChanged(it)
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            placeholder = { Text(text = "Mitarbeiter Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null
                )

            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
    }

    @Composable
    fun PasswordTextField(model: LoginViewModel) {

        var passwordVisible by rememberSaveable() { mutableStateOf(false) }

        TextField(
            value = model.userPassword,
            onValueChange = { model.onPasswordChanges(it) },
            label = { Text("Passwort") },
            singleLine = true,
            placeholder = { Text(text = "Passwort") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Send
            ), keyboardActions = KeyboardActions(
                onSend = {
                    if (model.userName.isNotBlank() && model.userPassword.isNotBlank()) {
                        switchToMain()
                    } else {

                        Toast.makeText(
                            this@LoginActivity,
                            "Fill in both Fields first!",
                            Toast.LENGTH_SHORT
                        )
                    }
                }
            ),

            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description =
                    if (passwordVisible) "Passwort ausblenden" else "Passwort anzeigen"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)

                }
            }
        )

    }

    @Preview
    @Composable
    fun PreviewScreen() {
        LoginScreen()
    }

    fun switchToMain() {
        auth.signInWithEmailAndPassword(model.userName, model.userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                    intent.putExtra("email_id", model.userName)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Benutzer oder Passwort falsch!", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

}
