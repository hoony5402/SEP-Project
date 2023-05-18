@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sep

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sep.screen.CalendarPage
import com.example.sep.screen.HomepagePage
import com.example.sep.screen.LoginPage
import com.example.sep.screen.MapPage
import com.example.sep.screen.MenuPage
import com.example.sep.screen.RegisterPage
import com.example.sep.ui.theme.SEPTheme
import com.google.firebase.auth.FirebaseAuth

private var auth: FirebaseAuth? = null
private var context: Context? = null
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContent {
            SEPTheme {

                auth = FirebaseAuth.getInstance()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.white)
                ) {
                    Box() {
                        ScreenMain()
                    }
                }
            }
        }
    }
}

@SuppressLint("Range")
@Composable
fun ScreenMain(){
    val navController = rememberNavController()

    //AutoLogin
    var auth = FirebaseAuth.getInstance()

    var dbHelper = DBHelper(context,"login.db",null,1)
    var database = dbHelper.writableDatabase
    var cursor = database.rawQuery("SELECT * FROM login;",null)
    var email = ""
    var password = ""
    while(cursor.moveToNext()) {
        email = cursor.getString(cursor.getColumnIndex("email"))
        cursor.moveToNext()
        password = cursor.getString(cursor.getColumnIndex("password"))
    }

    if(email != "" && password != ""){
        Toast.makeText(context,"Login Now...", Toast.LENGTH_SHORT).show()
        //TODO: from auth to user
        auth.signInWithEmailAndPassword(email.toString(),password.toString())
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    Toast.makeText(context,"Login Success", Toast.LENGTH_SHORT).show()
                    navController.navigate(Routes.Homepage.route)
                }else{
                    Toast.makeText(context,"Login Failed"+email.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            LoginPage(navController = navController)
        }

        composable(Routes.Register.route) {
            RegisterPage(navController = navController)
        }

        composable(Routes.Homepage.route) {
            HomepagePage(navController = navController)
        }

        composable(Routes.Map.route) {
            MapPage(navController = navController)
        }

        composable(Routes.Calendar.route) {
            CalendarPage(navController = navController)
        }

        composable(Routes.Menu.route) {
            MenuPage(navController = navController)
        }
    }
}