package com.example.sep.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.example.sep.DBHelper

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PostPage_Homepage(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val selected = remember { mutableStateOf(BottomIcons.HOME) }

    val title = "Generic Title"
    val description = "Generic description for the generic title. Generic description for the generic title. Generic description for the generic title."
    val date = "24/06/2023"
    val time = "02:05 pm"
    val location = "Generic Location, Generic Address"
    val image = "https://logowik.com/content/uploads/images/gist-gwangju-institute-of-science-and-technology9840.jpg"
    val type = "Announcements"

    val dbHelper: DBHelper = DBHelper(context, "posts.db", null, 1)

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
                                Toast.makeText(context, "CALENDAR Clicked", Toast.LENGTH_SHORT)
                                    .show()
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
                                Toast.makeText(context, "HOME Clicked", Toast.LENGTH_SHORT)
                                    .show()
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
                                Toast.makeText(context, "MAP Clicked", Toast.LENGTH_SHORT)
                                    .show()
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
        Column (
            modifier = Modifier.padding(paddingValues).fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

            Text(
                text = type,
                textAlign = TextAlign.Center,
                fontSize = (screenHeight/859.0 * 30).sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

            Card(
                modifier = Modifier
                    .padding(
                        (screenHeight / 859.0 * 20).dp,
                        (screenHeight / 859.0 * 5).dp,
                        (screenHeight / 859.0 * 20).dp,
                        0.dp
                    )
                    .size((screenWidth / 411.0 * 380).dp, (screenHeight / 859.0 * 500).dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape((screenHeight / 859.0 * 25).dp))
                    .paint(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(image)
                                .scale(Scale.FIT)
                                .build()
                        ),
                        contentScale = ContentScale.Crop
                    ),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.black70)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            (screenHeight / 859.0 * 20).dp,
                            (screenHeight / 859.0 * 20).dp,
                            (screenHeight / 859.0 * 20).dp,
                            0.dp
                        )
                        .size((screenWidth / 411.0 * 380).dp, (screenHeight / 859.0 * 500).dp)
                ) {
                    Text(
                        text = title + " " + MainActivity.clickflag.toString(),
                        textAlign = TextAlign.Left,
                        fontSize = (screenHeight/859.0 * 25).sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_rounded_bold)),
                        color = colorResource(R.color.white),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))
                    Text(
                        text = description,
                        textAlign = TextAlign.Left,
                        fontSize = (screenHeight/859.0 * 18).sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                        color = colorResource(R.color.white)
                    )
                    Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))
                    Text(
                        text = date + " - " + time,
                        textAlign = TextAlign.Left,
                        fontSize = (screenHeight/859.0 * 18).sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                        color = colorResource(R.color.white)
                    )
                    Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))
                    Text(
                        text = location,
                        textAlign = TextAlign.Left,
                        fontSize = (screenHeight/859.0 * 18).sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                        color = colorResource(R.color.white)
                    )
                }
            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 40).dp))

            Button(
                onClick = {
                    var i = MainActivity.clickflag
                    Toast.makeText(context, "title " + i.toString() + " clicked", Toast.LENGTH_SHORT).show()
                    var database = dbHelper.writableDatabase

                    var count = 0
                    var cursor = database.rawQuery("SELECT count(*) FROM posts WHERE id = ? AND type = ?", arrayOf(i.toString(), type))
                    if (cursor.moveToFirst()) count = cursor.getInt(0)

                    if (count == 0) database.execSQL("INSERT INTO posts(id,type,title,description,date,time,location) values('${i}','${type}','${title}','${description}','${date}','${time}','${location}');")
                    else Toast.makeText(context, "Already Exist", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
                modifier = Modifier
                    .width((screenWidth / 411.0 * 300).dp)
                    .height((screenHeight / 859.0 * 50).dp)
            ) {
                Text(
                    text = "add to calendar",
                    fontSize = (screenHeight/859.0 * 20).sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                )
            }
        }
    }
}
