package com.example.sep.screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSelect(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val selected = remember { mutableStateOf(BottomIcons.MAP) }

    var locationName by remember { mutableStateOf("") }

    var location by remember{mutableStateOf(LatLng(MainActivity.lat, MainActivity.long))}

    if(MainActivity.locName==""){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        LaunchedEffect(Unit) {
            try {
                // 위치 정보 권한 확인
                val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
                val hasLocationPermission = ActivityCompat.checkSelfPermission(context, locationPermission) == PackageManager.PERMISSION_GRANTED

                if (hasLocationPermission) {
                    // 위치 정보를 가져올 수 있는 경우
                    val loc = fusedLocationClient.lastLocation.await()
                    MainActivity.lat = loc.latitude
                    MainActivity.long = loc.longitude
                    MainActivity.locName = "current"
                    location = LatLng(MainActivity.lat, MainActivity.long)
                } else {
                    // 위치 정보 권한이 없는 경우 권한 요청
                    ActivityCompat.requestPermissions(context as Activity, arrayOf(locationPermission), LOCATION_PERMISSION_REQUEST_CODE)
                }
            } catch (e: Exception) {
                // 위치 정보를 가져오는 도중 오류 발생
                Toast.makeText(context, "Failed to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
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

        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))

            Text(
                text = "tap to select pin location",
                fontSize = (screenHeight/859.0 * 20).sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
            )

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 10).dp))


            var marker_state by remember {mutableStateOf(MarkerState(position = location))}

            GoogleMap(
                modifier = Modifier
                    .width((screenWidth / 411.0 * 420).dp)
                    .height((screenHeight / 859.0 * 500).dp)
                    .padding((screenWidth / 411.0 * 20).dp, 0.dp, (screenWidth / 411.0 * 20).dp, 0.dp)
                    .clip(RoundedCornerShape((screenHeight / 859.0 * 20).dp)),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    marker_state = MarkerState(position = latLng)
                },
            ) {
                Marker(
                    state = MarkerState(position = location),
                    title = "You",
                    snippet = "Marker"
                )

                Marker(
                    state = marker_state,
                    title = "You",
                    snippet = "Marker"
                )
            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

            TextField(
                label = null,
                value = locationName,
                onValueChange = { locationName = it },
                placeholder = {
                    Text(
                        text = "enter location name",
                        fontSize = (screenHeight/859.0 * 16).sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                        color = colorResource(R.color.white2)
                    )
                },
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
                    .width((screenWidth / 411.0 * 370).dp)
                    .height((screenHeight / 859.0 * 60).dp),
            )

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

            Button(
                onClick = {
                    MainActivity.lat = marker_state.position.latitude
                    MainActivity.long = marker_state.position.longitude
                    if(locationName=="") locationName="location"
                    MainActivity.locName = locationName
                    navController.popBackStack()
                },
                shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
                modifier = Modifier
                    .width((screenWidth / 411.0 * 300).dp)
                    .height((screenHeight / 859.0 * 50).dp)
            ) {
                Text(
                    text = "select",
                    fontSize = (screenHeight/859.0 * 20).sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                )
            }
        }
    }
}

