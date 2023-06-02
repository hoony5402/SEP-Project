package com.example.sep.screen

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(navController: NavHostController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colorResource(R.color.white))

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    var auth = FirebaseAuth.getInstance()

    var user = auth.currentUser

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    if(user!=null){
        login_success(user.email.toString(),context)
        Toast.makeText(context,user.email+" login success", Toast.LENGTH_SHORT).show()
        navController.navigate(Routes.Homepage.route)
    }else{
        MainActivity.userdata.reset()
    }

    Box(
        modifier = Modifier
            .padding(30.dp, (screenHeight / 859.0 * 380).dp, 30.dp, 0.dp)
            .size(width = (screenWidth / 411.0 * 600).dp, height = (screenHeight / 859.0 * 435).dp)
            .clip(shape = RoundedCornerShape(size = (screenHeight / 859.0 * 50).dp))
            .background(color = colorResource(R.color.color5))
    ) {
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

        Image(
            painter = painterResource(id = R.drawable.gistagram),
            contentDescription = null,
            modifier = Modifier.size((screenHeight/859.0 * 190).dp)
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))

        Text(text = "Welcome to the GIST community!",
            textAlign = TextAlign.Center,
            fontSize = (screenHeight/859.0 * 20).sp,
            style = TextStyle(fontSize = (screenHeight/859.0 * 18).sp),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
        )

        Text(text = "Login or Register to get started",
            textAlign = TextAlign.Center,
            fontSize = (screenHeight/859.0 * 20).sp,
            style = TextStyle(fontSize = (screenHeight/859.0 * 18).sp),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 110).dp))

        TextField(
            label = {
                Text(
                    text = "email",
                    fontSize = (screenHeight/859.0 * 16).sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                    color = colorResource(R.color.white2)
                )
            },
            value = email,
            placeholder = null,
            onValueChange = { email = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)), fontSize = (screenHeight/859.0 * 18).sp),
            shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
            modifier = Modifier
                .width((screenWidth / 411.0 * 280).dp)
                .height((screenHeight / 859.0 * 70).dp)
                .imePadding(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 50).dp))
        TextField(
            label = {
                Text(
                    text = "password",
                    fontSize = (screenHeight/859.0 * 16).sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                    color = colorResource(R.color.white2)
                )
            },
            value = password,
            placeholder = null,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)), fontSize = (screenHeight/859.0 * 18).sp),
            shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
            modifier = Modifier
                .width((screenWidth / 411.0 * 280).dp)
                .height((screenHeight / 859.0 * 70).dp)
                .align(Alignment.CenterHorizontally)
                .imePadding(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.moveFocus(FocusDirection.Enter)
                    auth.signInWithEmailAndPassword(email.toString(),password.toString())
                        .addOnCompleteListener{task->
                            if(task.isSuccessful){
                                Toast.makeText(context,"Login Success", Toast.LENGTH_SHORT).show()
                                user = auth.currentUser
                                login_success(email.toString(),context)
                                navController.navigate(Routes.Homepage.route)
                            }else{
                                Toast.makeText(context,"Login Failed"+email.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            )
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 60).dp))

        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email.toString(),password.toString())
                    .addOnCompleteListener{task->
                        if(task.isSuccessful){
                            Toast.makeText(context,"Login Success", Toast.LENGTH_SHORT).show()
                            user = auth.currentUser
                            login_success(email.toString(),context)
                            navController.navigate(Routes.Homepage.route)
                        }else{
                            Toast.makeText(context,"Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
            modifier = Modifier
                .width((screenWidth / 411.0 * 300).dp)
                .height((screenHeight / 859.0 * 50).dp)
        ) {
            Text(
                text = "login",
                fontSize = (screenHeight/859.0 * 18).sp,
                color = colorResource(R.color.white),
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
            )
        }

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

        ClickableText(
            text = AnnotatedString("not a user?"),
            onClick = {navController.navigate(Routes.Register.route)},
            style = TextStyle(
                fontSize = (screenHeight/859.0 * 14).sp,
                textDecoration = TextDecoration.Underline,
                color = colorResource(R.color.white),
                fontFamily = FontFamily(Font(R.font.sf_pro_text_semibold))
            )
        )
    }
}

fun login_success(email:String,cont:Context){
    var db :FirebaseDatabase = FirebaseDatabase.getInstance("https://sep-database-2a67a-default-rtdb.asia-southeast1.firebasedatabase.app/")
    var ref = db.reference.child("users")
    var keyuser = ""
    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for(children in dataSnapshot.children){
                if(children.child("email").getValue().toString()==email){
                    keyuser = children.key.toString()
                }
            }
            ref.child(keyuser.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot2: DataSnapshot) {
                    MainActivity.userdata.set(
                        dataSnapshot2.child("name").getValue().toString(),
                        dataSnapshot2.child("email").getValue().toString(),
                        dataSnapshot2.child("studentid").getValue().toString(),
                        dataSnapshot2.child("password").getValue().toString()
                    )
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
/*
    ref.get().addOnCompleteListener {
        if(it.isSuccessful){
            Toast.makeText(cont,"listener2"+keyuser, Toast.LENGTH_SHORT).show()
            for(children in it.result.children){
                if(children.child("email").getValue().toString()==email){
                    keyuser = children.key.toString()
                }
            }
        }
    }
    Toast.makeText(cont,"Login WOW"+keyuser, Toast.LENGTH_SHORT).show()
    */
}
