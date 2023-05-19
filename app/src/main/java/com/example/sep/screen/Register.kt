package com.example.sep.screen

import android.annotation.SuppressLint
import android.util.Half.toFloat
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sep.R
import com.example.sep.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth

private var auth: FirebaseAuth? = null

@SuppressLint("HalfFloat")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterPage(navController: NavHostController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colorResource(R.color.white))

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    auth = FirebaseAuth.getInstance()

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .padding(
                (screenHeight / 859.0 * 30).dp,
                (screenHeight / 859.0 * 160).dp,
                (screenHeight / 859.0 * 30).dp,
                0.dp
            )
            .size(width = (screenWidth / 411.0 * 600).dp, height = (screenHeight / 859.0 * 550).dp)
            .clip(shape = RoundedCornerShape(size = (screenHeight/859.0 * 50).dp))
            .background(color = colorResource(R.color.color5))
    ) {
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {

        var name by remember { mutableStateOf("") }
        var studentID by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var reenterpassword by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))

        Image(
            painter = painterResource(id = R.drawable.gistagram),
            contentDescription = null,
            modifier = Modifier
                .size((screenHeight/859.0 * 150).dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

        //Name
        TextField(
            label = {
                Text(
                    text = "name",
                    fontSize = (screenHeight/859.0 * 16).sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                    color = colorResource(R.color.white2)
                )
            },
            value = name,
            onValueChange = { name = it },
            placeholder = null,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)), fontSize = (screenWidth/411.0 * 18).sp),
            shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
            modifier = Modifier
                .width((screenWidth / 411.0 * 280).dp)
                .height((screenHeight / 859.0 * 60).dp)
                .align(Alignment.CenterHorizontally)
                .imePadding(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

        //Student ID
        TextField(
            label = {
                Text(
                    text = "student ID",
                    fontSize = (screenHeight/859.0 * 16).sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                    color = colorResource(R.color.white2)
                )
            },
            value = studentID,
            onValueChange = { studentID = it },
            placeholder = null,
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
                .height((screenHeight / 859.0 * 60).dp)
                .align(Alignment.CenterHorizontally)
                .imePadding(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

        //Email
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
            onValueChange = { email = it },
            placeholder = null,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)), fontSize = (screenHeight / 859.0 * 18).sp),
            shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
            modifier = Modifier
                .width((screenWidth / 411.0 * 280).dp)
                .height((screenHeight / 859.0 * 60).dp)
                .align(Alignment.CenterHorizontally)
                .imePadding(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

        //Password
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
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { password = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)), fontSize = (screenHeight / 859.0 * 18).sp),
            shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
            modifier = Modifier
                .width((screenWidth / 411.0 * 280).dp)
                .height((screenHeight / 859.0 * 60).dp)
                .align(Alignment.CenterHorizontally)
                .imePadding(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

        //Reenter Password
        TextField(
            label = {
                Text(
                    text = "re-enter password",
                    fontSize = (screenHeight/859.0 * 16).sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                    color = colorResource(R.color.white2)
                )
            },
            value = reenterpassword,
            placeholder = null,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { reenterpassword = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.color6),
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.transparent),
                unfocusedIndicatorColor = colorResource(R.color.transparent),
                disabledIndicatorColor = colorResource(R.color.transparent)
            ),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)), fontSize = (screenHeight / 859.0 * 18).sp),
            shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
            modifier = Modifier
                .width((screenWidth / 411.0 * 280).dp)
                .height((screenHeight / 859.0 * 60).dp)
                .align(Alignment.CenterHorizontally)
                .imePadding(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.moveFocus(FocusDirection.Enter)
                    auth!!.createUserWithEmailAndPassword(
                        email.toString(),
                        password.toString()
                    )
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate(Routes.Login.route)
                            } else {
                                Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            )
        )

        Spacer(modifier = Modifier.height((screenHeight/859.0 * 100).dp))

        Button(
            onClick = {
                if (password != reenterpassword) {
                    Toast.makeText(context, "Password is wrong", Toast.LENGTH_SHORT).show()
                } else {
                    auth!!.createUserWithEmailAndPassword(
                        email.toString(),
                        password.toString()
                    )
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate(Routes.Login.route)
                            } else {
                                Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            },
            shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
            modifier = Modifier
                .width((screenWidth / 411.0 * 300).dp)
                .height((screenHeight / 859.0 * 50).dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "register",
                fontSize = (screenHeight/859.0 * 18).sp,
                color = colorResource(R.color.white),
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
            )
        }
    }
}

