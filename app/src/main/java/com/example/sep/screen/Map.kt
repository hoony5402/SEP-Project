package com.example.sep.screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.navigation.NavHostController
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await

val LOCATION_PERMISSION_REQUEST_CODE = 100

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MapPage(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val selected = remember { mutableStateOf(BottomIcons.MAP) }

    var location = LatLng(MainActivity.lat, MainActivity.long)

    if (location != null) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location!!, 15f)
        }

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
                        ) {
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(id = R.color.black30),
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,

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
                        .clip(
                            RoundedCornerShape(
                                (screenHeight / 859.0 * 20).dp,
                                (screenHeight / 859.0 * 20).dp,
                                0.dp,
                                0.dp
                            )
                        ),
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
                                    tint = if (selected.value == BottomIcons.CALENDAR) colorResource(
                                        R.color.black
                                    ) else colorResource(R.color.black50)
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
                                    tint = if (selected.value == BottomIcons.HOME) colorResource(R.color.black) else colorResource(
                                        R.color.black50
                                    )
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
                                    tint = if (selected.value == BottomIcons.MAP) colorResource(R.color.black) else colorResource(
                                        R.color.black50
                                    )
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier.padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                var marker_state by remember {mutableStateOf(MarkerState(position = location!!))}

                GoogleMap(
                    modifier = Modifier
                        .width(screenWidth.dp)
                        .height(screenHeight.dp - (screenHeight/859.0 * 200).dp)
                        .padding((screenHeight/859.0 * 20).dp, (screenHeight/859.0 * 20).dp, (screenHeight/859.0 * 20).dp, (screenHeight/859.0 * 20).dp)
                        .clip(RoundedCornerShape((screenHeight/859.0 * 20).dp)),
                    cameraPositionState = cameraPositionState,
                    onMapClick = { latLng ->
                        marker_state = MarkerState(position = latLng)
                    },
                ) {
                    Marker(
                        state = MarkerState(position = location!!),
                        title = "You",
                        snippet = "Marker"
                    )

                    Marker(
                        state = marker_state,
                        title = "You",
                        snippet = "Marker"
                    )
                }

                Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))

                Button(
                    onClick = {
                              context.openMap(location.latitude, location.longitude)
                    },
                    shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
                    modifier = Modifier
                        .width((screenWidth / 411.0 * 300).dp)
                        .height((screenHeight / 859.0 * 50).dp)
                ) {
                    Text(
                        text = "open in maps",
                        fontSize = (screenHeight/859.0 * 20).sp,
                        color = colorResource(R.color.white),
                        fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                    )
                }
            }




        }
    }
}

fun Context.openMap(latitude: Double, longitude: Double) {

    //geo:0,0?q=-33.8666,151.1957(Google+Sydney)

    var area = "geo:0,0?q=" + latitude.toString() + "," + longitude.toString()

    // Creates an Intent that will load a map of San Francisco
    val gmmIntentUri = Uri.parse(area)
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(mapIntent)

//    try {
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
//        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
//        startActivity(intent)
//    } catch (e: ActivityNotFoundException) {
//        // TODO: Handle case where no email app is available
//    } catch (t: Throwable) {
//        // TODO: Handle potential other type of exceptions
//    }
}
