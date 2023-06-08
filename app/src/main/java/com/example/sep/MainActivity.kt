@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sep

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sep.screen.CalendarPage
import com.example.sep.screen.HomepagePage
import com.example.sep.screen.Image
import com.example.sep.screen.LOCATION_PERMISSION_REQUEST_CODE
import com.example.sep.screen.LoginPage
import com.example.sep.screen.MapPage
import com.example.sep.screen.MapSelect
import com.example.sep.screen.MenuPage
import com.example.sep.screen.PostPage_Calendar
import com.example.sep.screen.PostPage_Homepage
import com.example.sep.screen.RegisterPage
import com.example.sep.screen.WritePost
import com.example.sep.ui.theme.SEPTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

private var auth: FirebaseAuth? = null

class MainActivity : ComponentActivity() {
    companion object {
        private val LOCATION_PERMISSION_REQUEST_CODE = 100
        var userdata: UserData = UserData()
        var clickflag: Int = -1
        var clicktype: String = ""
        var lat: Double = 0.0
        var long: Double = 0.0
        var locName: String = ""
        var shopMark: ArrayList<List<String>> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocationPermission()

        setContent {
            SEPTheme {

                auth = FirebaseAuth.getInstance()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.white)
                ) {
                    Box() {
                        ScreenMain()
                    }
                }
            }
        }
    }

    private fun checkLocationPermission() {
        // 위치 정보 권한이 이미 허용되었는지 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED
        ) {
            // 권한이 거절되었을 경우 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // 권한이 이미 허용되어 있는 경우 위치 정보 사용 가능한 상태로 처리
            // 여기에 위치 정보를 사용하는 기능을 구현하면 됩니다.
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // 위치 정보 권한에 대한 사용자의 응답을 확인
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                // 위치 정보 권한이 허용된 경우 위치 정보 사용 가능한 상태로 처리
                // 여기에 위치 정보를 사용하는 기능을 구현하면 됩니다.
            } else {
                // 위치 정보 권한이 거절된 경우 앱 종료
                finish()
            }
        }
    }
}

@Composable
fun ScreenMain(){
    val navController = rememberNavController()

    val context = LocalContext.current

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        try {
            // 위치 정보 권한 확인
            val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
            val hasLocationPermission = ActivityCompat.checkSelfPermission(context, locationPermission) == PERMISSION_GRANTED

            if (hasLocationPermission) {
                // 위치 정보를 가져올 수 있는 경우
                val location = fusedLocationClient.lastLocation.await()
                MainActivity.lat = location.latitude
                MainActivity.long = location.longitude
            } else {
                // 위치 정보 권한이 없는 경우 권한 요청
                ActivityCompat.requestPermissions(context as Activity, arrayOf(locationPermission), LOCATION_PERMISSION_REQUEST_CODE)
            }
        } catch (e: Exception) {
            // 위치 정보를 가져오는 도중 오류 발생
            Toast.makeText(context, "Failed to get current location", Toast.LENGTH_SHORT).show()
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colorResource(R.color.white))

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            LoginPage(navController = navController)
        }

        composable(Routes.Register.route) {
            RegisterPage(navController = navController)
        }

        composable(Routes.Homepage.route) {
            HomepagePage(navController = navController)
        }

        composable(Routes.Map.route) {
            MapPage(navController = navController)
        }

        composable(Routes.Calendar.route) {
            CalendarPage(navController = navController)
        }

        composable(Routes.Menu.route) {
            MenuPage(navController = navController)
        }

        composable(Routes.WritePost.route) {
            WritePost(navController = navController)
        }

        composable(Routes.Post_Homepage.route) {
            PostPage_Homepage(navController = navController)
        }

        composable(Routes.Post_Calendar.route) {
            PostPage_Calendar(navController = navController)
        }

        composable(Routes.MapSelect.route) {
            MapSelect(navController = navController)
        }

        composable(Routes.Image.route) {
            Image(navController = navController)
        }
    }
}