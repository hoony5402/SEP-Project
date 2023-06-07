package com.example.sep.screen

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.sep.MainActivity
import com.example.sep.R
import com.example.sep.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WritePost(navController: NavHostController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colorResource(R.color.black30))

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    val selected = rememberSaveable { mutableStateOf(BottomIcons.HOME) }

    var title by rememberSaveable {mutableStateOf("")}
    var description by rememberSaveable {mutableStateOf("")}
    var time by rememberSaveable {mutableStateOf("")}
    var location by rememberSaveable {mutableStateOf("")}
    var type by rememberSaveable {mutableStateOf("")}
    var image by rememberSaveable {mutableStateOf<Uri?>(null)}

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            image = uri
        }

    var submit_button_Enabled by remember { mutableStateOf(true) }

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)+1
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = rememberSaveable { mutableStateOf("") }

    var day: String by rememberSaveable {mutableStateOf("")}
    var month: String by rememberSaveable {mutableStateOf("")}
    var year: String by rememberSaveable {mutableStateOf("")}

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val mMonth2 = mMonth+1

            day = "$mDayOfMonth"

            month = "$mMonth2"

            year = "$mYear"

            mDate.value = day + "/" + month + "/" + year

        }, mYear, mMonth, mDay
    )

    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = rememberSaveable { mutableStateOf("") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->

            val hour: String
            val minute: String
            val ampm: String

            val temp_hour: Int

            if (mHour > 12)
            {
                ampm = " pm"
                temp_hour = mHour - 12
                hour = "$temp_hour"
            }
            else
            {
                if (mHour == 0)
                {
                    hour = "12"
                    ampm = " am"
                }
                else if (mHour == 12)
                {
                    hour = "$mHour"
                    ampm = " pm"
                }
                else
                {
                    hour = "$mHour"
                    ampm = " am"
                }
            }

            if (mMinute < 9)
            {
                minute = "0$mMinute"
            }
            else
            {
                minute = "$mMinute"
            }

            mTime.value = hour + ":" + minute + ampm

        }, mHour, mMinute, false
    )
    var locationName by remember { mutableStateOf("") }

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
                                tint = if (selected.value == BottomIcons.CALENDAR) colorResource(R.color.black) else colorResource(
                                    R.color.black50
                                )
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
                                tint = if (selected.value == BottomIcons.HOME) colorResource(R.color.black) else colorResource(
                                    R.color.black50
                                )
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

        Box(
            modifier = Modifier
                .padding(
                    (screenHeight / 859.0 * 30).dp,
                    (screenHeight / 859.0 * 100).dp,
                    (screenHeight / 859.0 * 30).dp,
                    0.dp
                )
                .size(
                    width = (screenWidth / 411.0 * 600).dp,
                    height = (screenHeight / 859.0 * 580).dp
                )
                .clip(shape = RoundedCornerShape(size = (screenHeight / 859.0 * 50).dp))
                .background(color = colorResource(R.color.color5))
        ) {
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((screenHeight/859.0 * 80).dp))

            val listItems = arrayOf("Information", "Announcements", "Events")

            val contextForToast = LocalContext.current.applicationContext

            // state of the menu
            var expanded by remember {
                mutableStateOf(false)
            }

            // remember the selected item
            var selectedItem by rememberSaveable {
                mutableStateOf(listItems[0])
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                // text field
                TextField(
                    value = selectedItem,
                    onValueChange = {selectedItem},
                    readOnly = true,
                    label = {
                        Text(
                            text = "type",
                            fontSize = (screenHeight/859.0 * 14).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white2)
                        )
                    },
                    placeholder = null,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
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
                        .width((screenWidth / 411.0 * 280).dp)
                        .height((screenHeight / 859.0 * 60).dp)
                        .align(Alignment.CenterHorizontally)
                        .imePadding()
                        .menuAnchor()
                )

                // menu
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // this is a column scope
                    // all the items are added vertically
                    listItems.forEach { selectedOption ->
                        // menu item
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = selectedOption,
                                    fontSize = (screenHeight/859.0 * 12).sp,
                                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                                    color = colorResource(R.color.white2)
                                )},
                            onClick = {
                                selectedItem = selectedOption
                                Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

            TextField(
                label = null,
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text(
                        text = "title",
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
                    .width((screenWidth / 411.0 * 280).dp)
                    .height((screenHeight / 859.0 * 60).dp)
                    .align(Alignment.CenterHorizontally)
                    .imePadding(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

            TextField(
                label = null,
                value = description,
                onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = "description",
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
                    .width((screenWidth / 411.0 * 280).dp)
                    .height((screenHeight / 859.0 * 160).dp)
                    .align(Alignment.CenterHorizontally)
                    .imePadding(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width((screenWidth / 411.0 * 280).dp)
            )
            {
                Button(
                    shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
                    modifier = Modifier
                        .width((screenWidth / 411.0 * 125).dp)
                        .height((screenHeight / 859.0 * 60).dp)
                        .padding(0.dp, 0.dp, 0.dp, 0.dp),
                    onClick = {mDatePickerDialog.show()},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color6))
                ) {

                    if (mDate.value == "")
                    {
                        Text(
                            text = "date",
                            fontSize = (screenHeight/859.0 * 16).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white2),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else
                    {
                        Text(
                            text = "${mDate.value}",
                            fontSize = (screenHeight/859.0 * 12).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Button(
                    shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
                    modifier = Modifier
                        .width((screenWidth / 411.0 * 125).dp)
                        .height((screenHeight / 859.0 * 60).dp),
                    onClick = {mTimePickerDialog.show()},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color6))
                ) {

                    if (mTime.value == "")
                    {
                        Text(
                            text = "time",
                            fontSize = (screenHeight/859.0 * 16).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white2),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else
                    {
                        Text(
                            text = "${mTime.value}",
                            fontSize = (screenHeight/859.0 * 14).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 30).dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width((screenWidth / 411.0 * 280).dp)
            )
            {
                /*
                TextField(
                    label = null,
                    value = location,
                    onValueChange = { location = it },
                    placeholder = {
                        Text(
                            text = "location",
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
                        .width((screenWidth / 411.0 * 125).dp)
                        .height((screenHeight / 859.0 * 60).dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                 */
                locationName = MainActivity.locName
                Button(
                    shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
                    modifier = Modifier
                        .width((screenWidth / 411.0 * 125).dp)
                        .height((screenHeight / 859.0 * 60).dp),
                    onClick = {
                        navController.navigate(Routes.MapSelect.route)
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color6))
                ) {

                    if (locationName == "")
                    {
                        Text(
                            text = "location",
                            fontSize = (screenHeight/859.0 * 16).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white2),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else
                    {
                        Text(
                            text = "${locationName}",
                            fontSize = (screenHeight/859.0 * 14).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }



                Button(
                    shape = RoundedCornerShape((screenHeight/859.0 * 20).dp),
                    modifier = Modifier
                        .width((screenWidth / 411.0 * 125).dp)
                        .height((screenHeight / 859.0 * 60).dp),
                    onClick = {
                        launcher.launch("image/*")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color6))
                ) {

                    if (image == null)
                    {
                        Text(
                            text = "no image",
                            fontSize = (screenHeight/859.0 * 16).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white2),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else
                    {
                        Text(
                            text = "image OK",
                            fontSize = (screenHeight/859.0 * 14).sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                /*

                TextField(
                    label = null,
                    value = image,
                    onValueChange = { image = it },
                    placeholder = {
                        Text(
                            text = "image",
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
                        .width((screenWidth / 411.0 * 125).dp)
                        .height((screenHeight / 859.0 * 60).dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )

                 */

            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 70).dp))

            Button(
                onClick = {
                    submit_button_Enabled = false
                    var db : FirebaseDatabase = FirebaseDatabase.getInstance("https://sep-database-2a67a-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    var ref : DatabaseReference = db.getReference("posts").child(selectedItem)
                    var num :Int = -1
                    var writer :String = ""
                    if(FirebaseAuth.getInstance().currentUser!=null){
                        writer = MainActivity.userdata.username.toString()
                    }
                    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val current_time = LocalDateTime.now().format(format)
                    location = MainActivity.lat.toString()+','+MainActivity.long.toString()
                    ref.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            num = dataSnapshot.child("number").getValue(Int::class.java)!!
                            if(image!=null) {
                                val storage =
                                    Firebase.storage.getReferenceFromUrl("gs://sep-database-2a67a.appspot.com")
                                val imageRef = storage.child("images").child(selectedItem)
                                    .child("image " + num.toString())
                                image?.let {
                                    imageRef.putFile(it).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val imgUrl = imageRef.downloadUrl
                                            imgUrl.addOnSuccessListener { uri ->
                                                ref.child("number").setValue(num + 1)
                                                ref.child(num.toString()).child("type")
                                                    .setValue(selectedItem)
                                                var ref2: DatabaseReference =
                                                    ref.child(num.toString())
                                                ref2.child("writer").setValue(writer)
                                                ref2.child("write time")
                                                    .setValue(current_time.toString())
                                                ref2.child("title").setValue(title)
                                                ref2.child("description").setValue(description)
                                                ref2.child("image").setValue(uri.toString())
                                                ref2.child("year").setValue(year)
                                                ref2.child("month").setValue(month)
                                                ref2.child("day").setValue(day)
                                                ref2.child("time").setValue(mTime.value.toString())
                                                ref2.child("location").setValue(location)
                                                ref2.child("locationName").setValue(locationName)
                                                submit_button_Enabled = true
                                                Toast.makeText(
                                                    context,
                                                    "Post Success",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                                navController.navigate(Routes.Homepage.route)
                                            }.addOnFailureListener {
                                                Toast.makeText(
                                                    context,
                                                    "Get Image Uri Failed",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Image Upload Failed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }else{
                                ref.child("number").setValue(num + 1)
                                ref.child(num.toString()).child("type")
                                    .setValue(selectedItem)
                                var ref2: DatabaseReference =
                                    ref.child(num.toString())
                                ref2.child("writer").setValue(writer)
                                ref2.child("write time")
                                    .setValue(current_time.toString())
                                ref2.child("title").setValue(title)
                                ref2.child("description").setValue(description)
                                ref2.child("image").setValue("")
                                ref2.child("year").setValue(year)
                                ref2.child("month").setValue(month)
                                ref2.child("day").setValue(day)
                                ref2.child("time").setValue(mTime.value.toString())
                                ref2.child("location").setValue(location)
                                ref2.child("locationName").setValue(locationName)
                                submit_button_Enabled = true
                                Toast.makeText(
                                    context,
                                    "Post Success",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                navController.navigate(Routes.Homepage.route)
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(context, "Post Failed", Toast.LENGTH_SHORT).show()
                            submit_button_Enabled = true
                        }
                    })
                },
                enabled = submit_button_Enabled,
                shape = RoundedCornerShape((screenHeight/859.0 * 15).dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.color1)),
                modifier = Modifier
                    .width((screenWidth / 411.0 * 300).dp)
                    .height((screenHeight / 859.0 * 50).dp)
            ) {
                Text(
                    text = "submit post",
                    fontSize = (screenHeight/859.0 * 18).sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.sf_pro_text_bold))
                )
            }
        }
    }
}
