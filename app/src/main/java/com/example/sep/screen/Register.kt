package com.example.sep.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sep.R
import com.google.firebase.auth.FirebaseAuth

private var auth: FirebaseAuth? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(navController: NavHostController) {

    auth = FirebaseAuth.getInstance()

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(30.dp, 160.dp, 30.dp, 0.dp)
            .size(width = 600.dp, height = 570.dp)
            .clip(shape = RoundedCornerShape(size = 50.dp))
            .background(color = colorResource(R.color.color5))
    ) {
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var password2 by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.gistagram),
            contentDescription = null,
            modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(40.dp))

        //Name
        TextField(
            label = null,
            value = username,
            onValueChange = { username = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))),
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        //Student ID
        TextField(
            label = null,
            value = username,
            onValueChange = { username = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))),
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        //Username
        TextField(
            label = null,
            value = username,
            onValueChange = { username = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))),
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        //Password
        TextField(
            label = null,
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))),
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        //Reenter Password
        TextField(
            label = null,
            value = password2,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password2 = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))),
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(70.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if (password != password2) {
                        Toast.makeText(context, "Password is wrong", Toast.LENGTH_SHORT).show
                    } else {
                        auth!!.createUserWithEmailAndPassword(
                            username.toString(),
                            password.toString()
                        )
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = "register",
                    fontSize = 18.sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                )
            }
        }
    }

    Column()
    {
        Spacer(modifier = Modifier.height(170.dp))

        Text(
            text = "name",
            style = TextStyle(fontSize = 18.sp),
            color = colorResource(R.color.white),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
            modifier = Modifier.padding(horizontal = 80.dp)
        )

        Spacer(modifier = Modifier.height(75.dp))

        Text(
            text = "student ID",
            style = TextStyle(fontSize = 18.sp),
            color = colorResource(R.color.white),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
            modifier = Modifier.padding(horizontal = 80.dp)
        )

        Spacer(modifier = Modifier.height(75.dp))

        Text(
            text = "username",
            style = TextStyle(fontSize = 18.sp),
            color = colorResource(R.color.white),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
            modifier = Modifier.padding(horizontal = 80.dp)
        )

        Spacer(modifier = Modifier.height(75.dp))

        Text(
            text = "password",
            style = TextStyle(fontSize = 18.sp),
            color = colorResource(R.color.white),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
            modifier = Modifier.padding(horizontal = 80.dp)
        )

        Spacer(modifier = Modifier.height(75.dp))

        Text(
            text = "re-enter password",
            style = TextStyle(fontSize = 18.sp),
            color = colorResource(R.color.white),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
            modifier = Modifier.padding(horizontal = 80.dp)
        )
    }
}

