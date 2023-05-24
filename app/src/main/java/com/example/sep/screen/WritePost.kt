package com.example.sep.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.database.Cursor
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sep.DBHelper
import com.example.sep.R
import com.example.sep.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*


@SuppressLint("Range")
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

    val selected = remember { mutableStateOf(BottomIcons.HOME) }

    var title by remember{mutableStateOf("")}
    var description by remember{mutableStateOf("")}
    var time by remember{mutableStateOf("")}
    var location by remember{mutableStateOf("")}
    var type by remember{mutableStateOf("")}
    var image by remember{mutableStateOf("")}

    val dbHelper: DBHelper = DBHelper(context, "posts.db", null, 1)

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
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

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
            var selectedItem by remember {
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
                            fontSize = (screenHeight/859.0 * 18).sp,
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
            }

            Spacer(modifier = Modifier.height((screenHeight/859.0 * 70).dp))

            Button(
                onClick = {
                    var database = dbHelper.writableDatabase
                    database.execSQL("INSERT INTO posts(type,title,description,date,time,location,image) values('${selectedItem}','${title}','${description}','${mDate.value}','${mTime.value}','${location}','${image}');")

                    /*
                    var cursor = database.rawQuery("SELECT * FROM posts;",null)
                    while(cursor.moveToNext()){
                        var _id = cursor.getString(cursor.getColumnIndex("id"))
                        var _type = cursor.getString(cursor.getColumnIndex("type"))
                        var _title = cursor.getString(cursor.getColumnIndex("title"))
                        var _desc = cursor.getString(cursor.getColumnIndex("description"))
                        var _date = cursor.getString(cursor.getColumnIndex("date"))
                        var _time = cursor.getString(cursor.getColumnIndex("time"))
                        var _location = cursor.getString(cursor.getColumnIndex("location"))
                        var _image = cursor.getString(cursor.getColumnIndex("image"))
                        Log.d("jisoo","  id:${_id}")
                        Log.d("jisoo","type:${_type}")
                        Log.d("jisoo","titl:${_title}")
                        Log.d("jisoo","desc:${_desc}")
                        Log.d("jisoo","date:${_date}")
                        Log.d("jisoo","time:${_time}")
                        Log.d("jisoo","loca:${_location}")
                        Log.d("jisoo","imag:${_image}")
                    }
                     */
                },
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