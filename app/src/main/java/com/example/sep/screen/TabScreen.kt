package com.example.sep.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun TabScreen(navController: NavController, type: String){

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val db : FirebaseDatabase = FirebaseDatabase.getInstance("https://sep-database-2a67a-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val ref = db.reference.child("posts")
    var data by remember { mutableStateOf<DataSnapshot?>(null) }
    var num by remember { mutableStateOf(0) }
    ref.child(type).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            data = dataSnapshot
            num = dataSnapshot.child("number").getValue(Int::class.java)!!
        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })

    Column {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            for (i in num-1 downTo 0) {
                Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))

                Card(
                    modifier = Modifier
                        .clickable{
                            MainActivity.clickflag = i
                            MainActivity.clicktype = type
                            navController.navigate(Routes.Post_Homepage.route)
                        }
                        .padding(
                            (screenHeight / 859.0 * 20).dp,
                            (screenHeight / 859.0 * 5).dp,
                            (screenHeight / 859.0 * 20).dp,
                            0.dp
                        )
                        .size((screenWidth / 411.0 * 380).dp, (screenHeight / 859.0 * 150).dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape((screenHeight / 859.0 * 25).dp))
                        .paint(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data?.child(i.toString())?.child("image")?.getValue().toString())
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
                            .size((screenWidth / 411.0 * 380).dp, (screenHeight / 859.0 * 150).dp)
                    ) {
                        Text(
                            text = data?.child(i.toString())?.child("title")?.getValue().toString(),
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 20).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_rounded_bold)),
                            color = colorResource(R.color.white)
                        )
                        Spacer(modifier = Modifier.height((screenHeight/859.0 * 10).dp))
                        Text(
                            text = data?.child(i.toString())?.child("day")?.getValue().toString() + "/"+
                                    data?.child(i.toString())?.child("month")?.getValue().toString()+"/"+
                                    data?.child(i.toString())?.child("year")?.getValue().toString()+" - "
                                    + data?.child(i.toString())?.child("time")?.getValue().toString(),
                            textAlign = TextAlign.Left,
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white)
                        )
                        Spacer(modifier = Modifier.height((screenHeight/859.0 * 10).dp))
                        Text(
                            text = data?.child(i.toString())?.child("locationName")?.getValue().toString(),
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