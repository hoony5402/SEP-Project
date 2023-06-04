package com.example.sep.screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.navigation.NavHostController
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

    //google map
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(Unit) {
        try {
            // 위치 정보 권한 확인
            val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
            val hasLocationPermission = ActivityCompat.checkSelfPermission(context, locationPermission) == PackageManager.PERMISSION_GRANTED

            if (hasLocationPermission) {
                // 위치 정보를 가져올 수 있는 경우
                val location = fusedLocationClient.lastLocation.await()
                currentLocation = LatLng(location.latitude, location.longitude)
            } else {
                // 위치 정보 권한이 없는 경우 권한 요청
                ActivityCompat.requestPermissions(context as Activity, arrayOf(locationPermission), LOCATION_PERMISSION_REQUEST_CODE)
            }
        } catch (e: Exception) {
            // 위치 정보를 가져오는 도중 오류 발생
            Toast.makeText(context, "Failed to get current location", Toast.LENGTH_SHORT).show()
        }
    }

    if (currentLocation != null) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation!!, 15f)
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
            var markers by remember { mutableStateOf(emptyList<MarkerState>()) }

            GoogleMap(
                modifier = Modifier.padding(paddingValues),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    markers = markers + MarkerState(position = latLng)
                },
            ) {
                Marker(
                    state = MarkerState(position = currentLocation!!),
                    title = "You",
                    snippet = "Marker"
                )

                markers.forEach { markerState ->
                    Marker(
                        state = markerState,
                        title = "Marker",
                        snippet = "Marker"
                    )
                }
            }
        }
    }
}
