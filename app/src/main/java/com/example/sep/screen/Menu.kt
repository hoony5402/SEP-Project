package com.example.sep.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sep.R
import com.example.sep.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MenuPage(navController: NavHostController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colorResource(R.color.black30))

    var auth = FirebaseAuth.getInstance()

    var user = auth.currentUser

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var studentID by remember { mutableStateOf("20238009") }

    if (user != null)
    {
        // Get info from DB here
        username = (user.displayName).toString()
        email = (user.email).toString()
    }


    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val selected = remember { mutableStateOf(BottomIcons.HOME) }

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
                                Toast.makeText(context, "MENU Clicked", Toast.LENGTH_SHORT)
                                    .show()
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

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 130).dp))

            Button(
                onClick = {
                    navController.navigate(Routes.WritePost.route)
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

                    navController.navigate(Routes.Login.route)
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