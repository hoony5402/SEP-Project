package com.example.sep.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.example.sep.DBHelper

@SuppressLint("Range")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Image(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val selected = remember { mutableStateOf(BottomIcons.HOME) }

    val i = MainActivity.clickflag
    val type = MainActivity.clicktype

    val dbHelper: DBHelper = DBHelper(context, "posts.db", null, 1)
    val database = dbHelper.writableDatabase
    val cursor = database.rawQuery("SELECT * FROM posts WHERE id = ? AND type = ?", arrayOf(i.toString(), type))

    cursor.moveToNext()
    val title = cursor.getString(cursor.getColumnIndex("title"))
    val description = cursor.getString(cursor.getColumnIndex("description"))
    val date = cursor.getString(cursor.getColumnIndex("date"))
    val time = cursor.getString(cursor.getColumnIndex("time"))
    val location = cursor.getString(cursor.getColumnIndex("location"))
    val locationName = cursor.getString(cursor.getColumnIndex("locationName"))
    val imageCheck = cursor.getString(cursor.getColumnIndex("image"))
    var image = "https://logowik.com/content/uploads/images/gist-gwangju-institute-of-science-and-technology9840.jpg"
    if(imageCheck!="") image=imageCheck

    MainActivity.locName = ""
    MainActivity.lat = 0.0
    MainActivity.long = 0.0

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
                                navController.navigate(Routes.Menu.route)
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
        },
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(R.color.color1),
                contentColor = colorResource(R.color.white2),
                modifier = Modifier
                    .height((screenHeight / 859.0 * 50).dp)
                    .clip(RoundedCornerShape((screenHeight / 859.0 * 20).dp, (screenHeight / 859.0 * 20).dp, 0.dp, 0.dp)),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                selected.value = BottomIcons.CALENDAR
                                navController.navigate(Routes.Calendar.route)
                            },
                            modifier = Modifier.size((screenHeight / 859.0 * 30).dp)
                        ) {
                            val delete = painterResource(id = R.drawable.calendar)
                            Icon(
                                painter = delete,
                                contentDescription = null,
                                modifier = Modifier.size((screenHeight / 859.0 * 100).dp),
                                tint = if (selected.value == BottomIcons.CALENDAR) colorResource(R.color.black) else colorResource(R.color.black50)
                            )
                        }
                        IconButton(
                            onClick = {
                                selected.value = BottomIcons.HOME
                                navController.navigate(Routes.Homepage.route)
                            },
                            modifier = Modifier.size((screenHeight / 859.0 * 30).dp)
                        ) {
                            val delete = painterResource(id = R.drawable.hut)
                            Icon(
                                painter = delete,
                                contentDescription = null,
                                modifier = Modifier.size((screenHeight / 859.0 * 100).dp),
                                tint = if (selected.value == BottomIcons.HOME) colorResource(R.color.black) else colorResource(R.color.black50)
                            )
                        }
                        IconButton(
                            onClick = {
                                selected.value = BottomIcons.MAP
                                navController.navigate(Routes.Map.route)
                            },
                            modifier = Modifier.size((screenHeight / 859.0 * 30).dp)
                        ) {
                            val delete = painterResource(id = R.drawable.place)
                            Icon(
                                painter = delete,
                                contentDescription = null,
                                modifier = Modifier.size((screenHeight / 859.0 * 100).dp),
                                tint = if (selected.value == BottomIcons.MAP) colorResource(R.color.black) else colorResource(R.color.black50)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
//        Column (
//            modifier = Modifier.fillMaxWidth(),
//            verticalArrangement = Arrangement.SpaceEvenly,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
////            Card(
////                modifier = Modifier
////                    .size((screenWidth / 411.0 * 380).dp, (screenHeight / 859.0 * 700).dp)
////                    .paint(
////                        painter = rememberAsyncImagePainter(
////                            model = ImageRequest
////                                .Builder(LocalContext.current)
////                                .data(image)
////                                .scale(Scale.FIT)
////                                .build()
////                        ),
////                        contentScale = ContentScale.Crop
////                    ),
////                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.transparent)
////                )
////            )
////            {
////
////            }
//
//            Image(
//                painter = rememberAsyncImagePainter(
//                    model = ImageRequest
//                        .Builder(LocalContext.current)
//                        .data(image)
//                        .scale(Scale.FIT)
//                        .build()
//                ),
//                contentDescription = null
//            )
//        }

        Box(
            modifier = Modifier.fillMaxSize().padding((screenHeight / 859.0 * 10).dp, (screenHeight / 859.0 * 10).dp, (screenHeight / 859.0 * 10).dp, (screenHeight / 859.0 * 10).dp),
            contentAlignment = Alignment.Center
        )
        {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(image)
                        .scale(Scale.FIT)
                        .build()
                ),
                contentDescription = null
            )
        }
    }
}
