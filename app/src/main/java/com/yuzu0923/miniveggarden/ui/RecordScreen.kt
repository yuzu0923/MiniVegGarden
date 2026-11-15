// 作業記録を確認する画面
package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel
import java.time.ZoneId
import java.time.Instant
import java.time.LocalDate
import kotlin.time.ExperimentalTime
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.ui.graphics.Color
import com.yuzu0923.miniveggarden.theme.GardenCream
import com.yuzu0923.miniveggarden.theme.GardenGreen
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable //この関数は画面として描画できます
fun RecordScreen(
    workRecordViewModel: WorkRecordViewModel,
    vegetableViewModel: VegetableViewModel
) {
    val today = LocalDate.now(ZoneId.of("Asia/Tokyo"))

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    )
    val selectedDate = datePickerState.selectedDateMillis?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.of("Asia/Tokyo"))
            .toLocalDate()
    }

    val formattedSelectedDate = selectedDate?.format(
        DateTimeFormatter.ofPattern(
            "　yyyy年MM月dd日",
            Locale.JAPAN
        )
    )

    val workRecords by workRecordViewModel
        .getWorkRecordsByDate(selectedDate.toString())
        .collectAsState(initial = emptyList())

    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // カレンダー表示
        DatePicker(
            state = datePickerState,
            showModeToggle = false,

            headline = {
                val headlineDate = datePickerState.selectedDateMillis?.let {
                        Instant.ofEpochMilli(it)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                }

                Text(
                    text = headlineDate?.format(
                        DateTimeFormatter.ofPattern("yyyy年MM月dd日")
                    ) ?: "日付を選択",
                    color = GardenGreen
                )
            },

            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                titleContentColor = GardenGreen,
                headlineContentColor = GardenGreen,
                weekdayContentColor = GardenGreen,
                navigationContentColor = GardenGreen,
                dayContentColor = GardenGreen,
                selectedDayContainerColor = GardenGreen,
                selectedDayContentColor = Color.White,
                todayContentColor = GardenGreen,
                todayDateBorderColor = GardenGreen,
                dividerColor = GardenCream
            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text("${formattedSelectedDate}の作業")

        val recordsByVegetable = workRecords.groupBy {
            it.vegetableId
        }

        recordsByVegetable.entries.forEachIndexed { index, (vegetableId, records) ->
            val vegetable = vegetables.find { it.vegetableId == vegetableId }

            val workIcons = records
                .distinctBy { it.workType }
                .map { record ->
                    when (record.workType) {
                        "水やり" -> "💧"
                        "施肥" -> "🌱"
                        "虫害" -> "🐛"
                        "葉枯れ" -> "🍂"
                        "収穫" -> "🥕"
                        else -> "📝"
                    }
                }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "${index + 1}．${vegetable?.vegetableName ?: "不明な作物"}　${workIcons.joinToString(" ")}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}