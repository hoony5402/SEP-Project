package com.example.sep.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.sep.R

class CardObject {
    public var title: String = "Generic Title"
    public var description: String = "Generic description for the generic title."
    public var time: String = "Generic date 24.06.2023 "
    public var location: String = "Generic Location, Generic Address"
    public var type: String = "Announcements"
    public var image: String = "https://logowik.com/content/uploads/images/gist-gwangju-institute-of-science-and-technology9840.jpg"
}

@Composable
fun TabScreen(
    content: CardObject
) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    Column() {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            for (i in 1..100) {
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                        .size(380.dp, 160.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(25.dp))
                        .paint(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(content.image)
                                    .scale(Scale.FIT)
                                    .build()
                            ),
                            contentScale = ContentScale.Crop
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.black70),
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp, 0.dp, 20.dp, 0.dp)
                            .size(380.dp, 160.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = content.title + " " + i.toString(),
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 20).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_rounded_bold)),
                            color = colorResource(R.color.white)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = content.description,
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                        Text(
                            text = content.time,
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                        Text(
                            text = content.location,
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                        Text(
                            text = content.type,
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewTabScreen() {
    TabScreen(content = CardObject())
}