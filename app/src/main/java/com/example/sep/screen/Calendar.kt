package com.example.sep.screen

import android.graphics.Paint
import android.icu.lang.UCharacter.DecompositionType.NARROW
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.example.sep.DBHelper
import com.example.sep.R
import com.example.sep.Routes
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.day.NonSelectableDayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.toKotlinYearMonth
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.DayOfWeek
import java.time.Year
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalendarPage(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val selected = remember { mutableStateOf(BottomIcons.CALENDAR) }

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
            modifier = Modifier.padding(paddingValues)
        ) {
            val temp_list: List<LocalDate> = listOf(LocalDate.now())

            var selection by remember { mutableStateOf(temp_list) }

            Column {

                val title = "Generic Title"
                val description = "Generic description for the generic title. Generic description for the generic title. Generic description for the generic title."
                val selectDate = selection[0].dayOfMonth.toString() + "/" + "%02d".format(selection[0].monthNumber) + "/" + selection[0].year.toString()
                val time = "02:05 pm "
                val location = "Generic Location, Generic Address"
                val image = "https://logowik.com/content/uploads/images/gist-gwangju-institute-of-science-and-technology9840.jpg"

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false)
                ) {
                    DateTimeCalendar(
                        today = LocalDate.now(),
                        onSelectionChanged = { selection = it },
                        dayContent = { DayContent(dayState = it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    //===================================================================
                    //for (i in 1..100) {
                    var database = dbHelper.writableDatabase
                    var cursor = database.rawQuery("SELECT * FROM posts;", null)
                    while (cursor.moveToNext()) {
                        var i = cursor.getString(cursor.getColumnIndex("id"))
                        var type = cursor.getString(cursor.getColumnIndex("type"))
                        val date = cursor.getString(cursor.getColumnIndex("date"))

                        Spacer(modifier = Modifier.height((screenHeight/859.0 * 20).dp))

                        if (selectDate != date) continue

                        Card(
                            modifier = Modifier
                                .clickable{
                                    MainActivity.clickflag = i.toInt()
                                    navController.navigate(Routes.Post_Calendar.route)
                                }
                                .padding((screenHeight/859.0 * 20).dp, (screenHeight/859.0 * 5).dp, (screenHeight/859.0 * 20).dp, 0.dp)
                                .size((screenWidth / 411.0 * 380).dp, (screenHeight/859.0 * 150).dp)
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
                                    .size((screenWidth / 411.0 * 380).dp, (screenHeight/859.0 * 150).dp)
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
                                    text = selectDate + " - " + time,
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
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun MonthHeader(monthState: MonthState) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp, 10.dp, 40.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                monthState.currentMonth = monthState.currentMonth.minusMonths(1)
            },
            modifier = Modifier.size((screenHeight / 859.0 * 20).dp)
        ) {
            val delete = painterResource(id = R.drawable.back)
            Icon(
                painter = delete,
                contentDescription = null,
                modifier = Modifier.size((screenHeight / 859.0 * 100).dp),
                tint = colorResource(R.color.color1)
            )
        }

        Text(
            monthState.currentMonth.month.name + " " + monthState.currentMonth.year.toString(),
            fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
            fontSize = (screenHeight/859.0 * 20).sp,
        )

        IconButton(
            onClick = {
                monthState.currentMonth = monthState.currentMonth.plusMonths(1)
              },
            modifier = Modifier.size((screenHeight / 859.0 * 20).dp)
        ) {
            val delete = painterResource(id = R.drawable.next)
            Icon(
                painter = delete,
                contentDescription = null,
                modifier = Modifier.size((screenHeight / 859.0 * 100).dp),
                tint = colorResource(R.color.color1)
            )
        }
    }
}

@Composable
private fun WeekHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp)
    ){
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(java.time.format.TextStyle.NARROW, Locale.ROOT),
                fontFamily = FontFamily(Font(R.font.sf_pro_text_bold)),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
private fun MonthContainer(content: @Composable (PaddingValues) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, colorResource(R.color.color1)),
        content = { content(PaddingValues(4.dp)) },
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.transparent)),
        modifier = Modifier.width(410.dp).padding(10.dp, 0.dp, 10.dp, 0.dp),
    )
}

@Composable
fun BoxScope.DayContent(
    dayState: KotlinDayState<DynamicSelectionState>,
) {
    val isSelected = dayState.selectionState.isDateSelected(dayState.date)

    val background = if (isSelected) colorResource(R.color.black30) else colorResource(R.color.transparent)

    val textcolour = if (dayState.date == LocalDate.now()) colorResource(R.color.color1) else if (isSelected) colorResource(R.color.white) else colorResource(R.color.black)

    Box (
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(background)
    )
    {
        Text(
            text = dayState.date.dayOfMonth.toString(),
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {
                    dayState.selectionState.onDateSelected(dayState.date)
                }
                .padding(0.dp, 15.dp, 0.dp, 0.dp),
            color = textcolour,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.sf_pro_text)),
        )
    }
}

@Composable
fun DateTimeCalendar(
    today: LocalDate,
    onSelectionChanged: (List<LocalDate>) -> Unit,
    dayContent: @Composable BoxScope.(KotlinDayState<DynamicSelectionState>) -> Unit
) {
    val temp_list: List<java.time.LocalDate> = listOf(today.toJavaLocalDate())
    SelectableCalendar(
        calendarState = rememberSelectableCalendarState(
            confirmSelectionChange = { selection -> onSelectionChanged(selection.map { it.toKotlinLocalDate() }); true },
            initialSelectionMode = SelectionMode.Single,
            initialSelection = temp_list
        ),
        today = today.toJavaLocalDate(),
        showAdjacentMonths = false,
        dayContent = { dayState ->
            dayContent(
                KotlinDayState(
                    date = dayState.date.toKotlinLocalDate(),
                    isCurrentDay = dayState.isCurrentDay,
                    selectionState = dayState.selectionState,
                )
            )
        },
        monthHeader = { MonthHeader(monthState = it) },
        daysOfWeekHeader = { WeekHeader(daysOfWeek = it) },
        monthContainer = { MonthContainer(it) },
    )
}

data class KotlinDayState<T : SelectionState>(
    val date: LocalDate,
    val isCurrentDay: Boolean,
    val selectionState: T,
)

private fun SelectionState.isDateSelected(date: LocalDate) = isDateSelected(date.toJavaLocalDate())
private fun SelectionState.onDateSelected(date: LocalDate) = onDateSelected(date.toJavaLocalDate())