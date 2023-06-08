package com.example.sep.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sep.DBHelper
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPage(navController: NavHostController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colorResource(R.color.black30))

    val auth = FirebaseAuth.getInstance()

    val user = auth.currentUser

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var studentID by remember { mutableStateOf("") }
    var usertype by remember { mutableStateOf("") }

    if (user != null)
    {
        // Get info from DB here
        username = MainActivity.userdata.username
        email = MainActivity.userdata.useremail
        studentID = MainActivity.userdata.studentid
        usertype = MainActivity.userdata.usertype

    }else{
        navController.navigate(Routes.Login.route)
    }


    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    Scaffold(
        containerColor = colorResource(R.color.white),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, (screenHeight / 859.0 * 10).dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                if(navController.popBackStack().not()) {
                                    navController.navigate(Routes.Homepage.route)
                                }
                            },
                            modifier = Modifier.size((screenHeight / 859.0 * 30).dp)
                        ) {
                            val delete = painterResource(id = R.drawable.menu)
                            Icon(
                                painter = delete,
                                contentDescription = null,
                                modifier = Modifier.size((screenHeight / 859.0 * 35).dp),
                                tint = colorResource(R.color.black)
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.gistagram2),
                            contentDescription = null,
                            modifier = Modifier
                                .size((screenHeight / 859.0 * 175).dp),

                            )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors (
                    containerColor = colorResource(id = R.color.black30),
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.height(50.dp)

            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((screenHeight/859.0 * 100).dp))

            Text(
                text = username,
                textAlign = TextAlign.Center,
                fontSize = (screenHeight/859.0 * 40).sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
            )

            Text(
                text = email,
                textAlign = TextAlign.Center,
                fontSize = (screenHeight/859.0 * 16).sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
            )

            Text(
                text = studentID,
                textAlign = TextAlign.Center,
                fontSize = (screenHeight/859.0 * 16).sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
            )
            Text(
                text = usertype,
                textAlign = TextAlign.Center,
                fontSize = (screenHeight/859.0 * 16).sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
            )

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 130).dp))

            Button(
                onClick = {
                    if(usertype!="Student") navController.navigate(Routes.WritePost.route)
                    else{
                        Toast.makeText(context, "Student can't post", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color2)),
                modifier = Modifier
                    .width((screenWidth / 411.0 * 300).dp)
                    .height((screenHeight / 859.0 * 50).dp)
            ) {
                Text(
                    text = "new post",
                    fontSize = (screenHeight/859.0 * 18).sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                )
            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 70).dp))

            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    val dbHelper = DBHelper(context, "posts.db", null, 1)
                    val database = dbHelper.writableDatabase
                    database.execSQL("INSERT or REPLACE INTO lastlogin VALUES('"+
                            MainActivity.userdata.useremail+"','"+
                            MainActivity.userdata.userpassword+"');")
                    navController.navigate(Routes.Login.route)
                },
                shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color2)),
                modifier = Modifier
                    .width((screenWidth / 411.0 * 300).dp)
                    .height((screenHeight / 859.0 * 50).dp)
            ) {
                Text(
                    text = "logout",
                    fontSize = (screenHeight/859.0 * 18).sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                )
            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 70).dp))

            Button(
                onClick = {

                    user?.delete()?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Account Deleted", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate(Routes.Login.route)
                        } else {
                            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT)
                                .show()
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
                    text = "delete account",
                    fontSize = (screenHeight/859.0 * 18).sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                )
            }
        }
    }
}