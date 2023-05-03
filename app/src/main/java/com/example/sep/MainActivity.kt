@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sep

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.sep.ui.theme.SEPTheme
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.typeOf

private var auth: FirebaseAuth? = null

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEPTheme {

                auth = FirebaseAuth.getInstance()

                var email_text by remember {
                    mutableStateOf("")
                }
                var password_text by rememberSaveable {
                    mutableStateOf("")
                }

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                }
                Column{
                    OutlinedTextField(value = email_text, onValueChange = { email_text = it}, label = { Text("Email")})
                    TextField(
                        value = password_text,
                        onValueChange = { password_text = it},
                        label = { Text("Password")},
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Button(onClick = {
                        auth!!.createUserWithEmailAndPassword(email_text.toString(),password_text.toString())
                    }) {
                        Text(text = "Register")
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Composable
fun SimpleText(modifier: Modifier=Modifier){
    Text("WOW",modifier=modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String){


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(){
    var password_text by rememberSaveable {
        mutableStateOf("")
    }
    TextField(
        value = password_text,
        onValueChange = { password_text = it},
        label = { Text("Password")},
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}
/*
@Composable
fun RegisterButton(){
    Button(onClick = { /*TODO*/ }) {
        auth!!.createUserWithEmailAndPassword(password)
    }
}
*/
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SEPTheme {
        Greeting("Android")
    }
}