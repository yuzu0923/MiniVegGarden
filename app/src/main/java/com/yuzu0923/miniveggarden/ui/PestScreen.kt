// 虫害記録画面
package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    val selectedVegetableIds = remember {
        mutableStateListOf<Int>()
    }

    // レイアウト
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("虫害があった作物")

        // 登録済みの作物を一覧で表示する（for文回し）
        vegetables.forEach { vegetable ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                Text(
                    text = vegetable.vegetableName,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
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
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("虫害を記録する")
        }

        // 「戻る」ボタン
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("戻る")
        }
    }
}