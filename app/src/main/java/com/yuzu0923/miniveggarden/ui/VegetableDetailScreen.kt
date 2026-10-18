// 作物詳細情報の変更を行う画面
package com.yuzu0923.miniveggarden.ui
// import
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun VegetableDetailScreen(
    vegetableId: Int?,
    vegetableViewModel: VegetableViewModel,
    workRecordViewModel: WorkRecordViewModel,
    navController: NavController
) {
    val vegetable by vegetableViewModel
        .getVegetableById(vegetableId!!) //Navigationで vegetableId を必ず渡す設計　ここでは Int? → Int
        .collectAsState(initial = null)

    val currentVegetable = vegetable //下記ifを書くため、一旦取得値を変数に格納する。

    val workRecords by workRecordViewModel
        .getWorkRecordsByVegetableId(currentVegetable?.vegetableId ?: -1) //-1 は、まだ作物データが取得できていないときに使う仮のID
        .collectAsState(initial = emptyList())

    val today = LocalDate.now(ZoneId.of("Asia/Tokyo"))
    val sevenDaysAgo = today.minusDays(6)

    val recentWorkRecords = workRecords
        .filter { record ->
            val workDate = runCatching {
                LocalDate.parse(record.workDate)
            }.getOrNull()

            workDate != null &&
                    !workDate.isBefore(sevenDaysAgo) &&
                    !workDate.isAfter(today)
        }
        .groupBy { it.workDate }
        .toSortedMap(compareByDescending { it })


    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (currentVegetable != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column {
                    Text(
                        text = "■作物名",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = currentVegetable.vegetableName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column {
                    Text(
                        text = "■場所",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${currentVegetable.row + 1}行目・${currentVegetable.col + 1}列目",
                        fontSize = 20.sp
                    )
                }

                Column {
                    Text(
                        text = "■畑ID",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = currentVegetable.fieldId.toString(),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 作業履歴の表示
            Text(
                text = "■直近7日間の作業履歴",
                fontSize = 20.sp
            )

            if (recentWorkRecords.isEmpty()) {
                Text(
                    text = "直近7日間の作業記録はありません。",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                recentWorkRecords.forEach { (workDate, records) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = workDate,
                                fontWeight = FontWeight.SemiBold
                            )

                            records
                                .distinctBy { it.workType }
                                .forEach { record ->
                                    val icon = when (record.workType) {
                                        "水やり" -> "💧"
                                        "肥料" -> "🌱"
                                        "虫害" -> "🐛"
                                        "葉枯れ" -> "🍂"
                                        "収穫" -> "🥕"
                                        else -> "📝"
                                    }

                                    val vegetableName =
                                        currentVegetable.vegetableName

                                    Text("$icon $vegetableName：${record.workType}")
                                }
                        }
                    }
                }
            }

            // 「作物情報を編集」ボタン
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp),
                    onClick = {
                        currentVegetable?.let { //解消保留
                            navController.navigate(
                                "vegetable_edit/${it.vegetableId}"
                            )
                        }
                    }
                ) {
                    Text(
                        text = "✏️ 作物情報を編集",
                        fontSize = 17.sp
                    )
                }

                // 「この作物を削除する」ボタン
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp),
                    onClick = {
                        showDeleteDialog = true
                    }
                ) {
                    Text(
                        text = "🗑️ この作物を削除する",
                        fontSize = 17.sp
                    )
                }

                // 「戻る」ボタン
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "戻る",
                        fontSize = 17.sp
                    )
                }
            }
        }
    }

    // 削除時の小窓
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text("作物を削除")
            },
            text = {
                Text("「${currentVegetable?.vegetableName}」を削除しますか？")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        currentVegetable?.let {
                            workRecordViewModel.deleteWorkRecordsByVegetableId(
                                it.vegetableId
                            )

                            vegetableViewModel.deleteVegetable(it)
                        }

                        showDeleteDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("やめる")
                }
            }
        )
    }
}