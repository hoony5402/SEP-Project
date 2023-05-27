package com.example.sep.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.sep.R
import com.example.sep.Routes

class CardObject {
    public var title: String = "Generic Title"
    public var description: String = "Generic description for the generic title. Generic description for the generic title. Generic description for the generic title."
    public var date: String = "24/06/2023 "
    public var time: String = "02:05 pm "
    public var location: String = "Generic Location, Generic Address"
    public var type: String = "Announcements"
    public var image: String = "https://logowik.com/content/uploads/images/gist-gwangju-institute-of-science-and-technology9840.jpg"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(content: CardObject) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val title = content.title
    val description = content.description
    val date = content.date
    val time = content.time
    val location = content.location
    val image = content.image

    Column() {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            for (i in 1..100) {
                Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))

                Card(
                    modifier = Modifier
                        .padding((screenHeight/859.0 * 20).dp, (screenHeight/859.0 * 5).dp, (screenHeight/859.0 * 20).dp, 0.dp)
                        .size((screenWidth / 411.0 * 380).dp, (screenHeight/859.0 * 300).dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape((screenHeight/859.0 * 25).dp))
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
                            .padding((screenHeight/859.0 * 20).dp, (screenHeight/859.0 * 20).dp, (screenHeight/859.0 * 20).dp, 0.dp)
                            .size((screenWidth / 411.0 * 380).dp, (screenHeight/859.0 * 300).dp)
                    ) {
                        Text(
                            text = title + " " + i.toString(),
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 20).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_rounded_bold)),
                            color = colorResource(R.color.white)
                        )
                        Spacer(modifier = Modifier.height((screenHeight/859.0 * 10).dp))
                        Text(
                            text = description,
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                        Spacer(modifier = Modifier.height((screenHeight/859.0 * 10).dp))
                        Text(
                            text = date + " - " + time,
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                        Spacer(modifier = Modifier.height((screenHeight/859.0 * 10).dp))
                        Text(
                            text = location,
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                        Spacer(modifier = Modifier.height((screenHeight/859.0 * 35).dp))
                        Button(
                            onClick = {
                                Toast.makeText(context, "title " + i + " clicked", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape((screenHeight/859.0 * 10).dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
                            modifier = Modifier
                                .width((screenWidth / 411.0 * 150).dp)
                                .height((screenHeight / 859.0 * 40).dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "add to calendar",
                                fontSize = (screenHeight/859.0 * 10).sp,
                                color = colorResource(R.color.white),
                                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}