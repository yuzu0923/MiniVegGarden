// 虫害記録画面
package com.yuzu0923.miniveggarden.ui

// import
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
fun PestScreen(
    vegetableViewModel: VegetableViewModel,
    workRecordViewModel: WorkRecordViewModel,
    navController: NavController
) {
    // 変数・定数の定義
    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())

    var selectedVegetableIds = remember {
        mutableStateListOf<Int>()
    }

    // レイアウト
    Column {
        Text("虫害があった作物")

        // 登録済みの作物を一覧で表示する（for文回し）
        vegetables.forEach { vegetable ->
            // 繰り返し内容
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

        // 作業記録をつける
        Button(
            enabled = selectedVegetableIds.isNotEmpty(),
            onClick = {
                val workRecords = mutableListOf<WorkRecordEntity>()

                selectedVegetableIds.forEach { vegetableId ->
                    workRecords.add(
                        WorkRecordEntity(
                            vegetableId = vegetableId,
                            workType = "虫害",
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
            Text("虫害を記録する")
        }

        // 戻るボタン
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("戻る")
        }
    }
}