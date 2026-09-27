package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Alignment
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable //この関数は画面として描画できます
fun RecordScreen(
    workRecordViewModel: WorkRecordViewModel,
    vegetableViewModel: VegetableViewModel
) {
    val today = LocalDate.now(ZoneId.of("Asia/Tokyo"))

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today
            .atStartOfDay(ZoneId.of("Asia/Tokyo"))
            .toInstant()
            .toEpochMilli()
    )
    val selectedDate = datePickerState.selectedDateMillis?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.of("Asia/Tokyo"))
            .toLocalDate()
    }


    val workRecords by workRecordViewModel
        .getWorkRecordsByDate(selectedDate.toString())
        .collectAsState(initial = emptyList())

    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())


    Column {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "${selectedDate?.monthValue}/${selectedDate?.dayOfMonth}の作業"
        )

        val recordsByVegetable = workRecords.groupBy {
            it.vegetableId
        }

        recordsByVegetable.entries.forEachIndexed { index, (vegetableId, records) ->

            val vegetable = vegetables.find {
                it.vegetableId == vegetableId
            }

            val workIcons = records
                .distinctBy { it.workType }
                .map { record ->
                    when (record.workType) {
                        "水やり" -> "💧"
                        "肥料" -> "🌱"
                        "虫害" -> "🐛"
                        "葉枯れ" -> "🍂"
                        "収穫" -> "🥕"
                        else -> "📝"
                    }
                }

            Text(
                text = "${index + 1}．${vegetable?.vegetableName ?: "不明な作物"}　${workIcons.joinToString(" ")}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}