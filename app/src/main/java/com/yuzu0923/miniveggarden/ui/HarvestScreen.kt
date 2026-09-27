package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.data.local.WorkRecordEntity
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun HarvestScreen(
    vegetableViewModel: VegetableViewModel,
    workRecordViewModel: WorkRecordViewModel,
    navController: NavController
) {
    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())

    var selectedVegetableIds = remember {
        mutableStateListOf<Int>()
    }

    Column {
        Text("収穫した作物")

        vegetables.forEach { vegetable ->
            Checkbox(
                checked = selectedVegetableIds.contains(vegetable.vegetableId),
                onCheckedChange = { checked ->
                    if (checked) {
                        selectedVegetableIds.add(vegetable.vegetableId)
                    } else {
                        selectedVegetableIds.remove(vegetable.vegetableId)
                    }
                }
            )

            Text(vegetable.vegetableName)
        }

        Button(
            onClick = {
                val workRecords = mutableListOf<WorkRecordEntity>()

                selectedVegetableIds.forEach { vegetableId ->
                    workRecords.add(
                        WorkRecordEntity(
                            vegetableId = vegetableId,
                            workType = "収穫",
                            workDate = LocalDate.now(
                                ZoneId.of("Asia/Tokyo")
                            ).toString()
                        )
                    )
                }

                workRecordViewModel.addWorkRecords(workRecords)

                navController.popBackStack()
            }
        ) {
            Text("収穫を記録する")
        }

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("戻る")
        }
    }
}