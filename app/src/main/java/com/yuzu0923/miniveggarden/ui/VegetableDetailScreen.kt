package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel

@Composable
fun VegetableDetailScreen(
    vegetableId: Int?,
    vegetableViewModel: VegetableViewModel,
    workRecordViewModel: WorkRecordViewModel,
    navController: NavController
) {
    val vegetable by vegetableViewModel
        .getVegetableById(vegetableId!!) //Navigationで vegetableId を必ず渡す設計だから、ここでは Int? → Int
        .collectAsState(initial = null)

    val currentVegetable = vegetable //下記ifを書くため、一旦取得値を変数に格納する。

    val workRecords by workRecordViewModel
        .getWorkRecordsByVegetableId(currentVegetable?.vegetableId ?: -1) //-1 は、まだ作物データが取得できていないときに使う仮のID
        .collectAsState(initial = emptyList())


    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Column {
        if (currentVegetable != null) {
            Text("作物名：${currentVegetable.vegetableName}")
            Text("場所：${currentVegetable.row + 1}行目・${currentVegetable.col + 1}列目")
            Text("畑ID：${currentVegetable.fieldId}")


            Text("作業履歴")

            workRecords
                .sortedByDescending { it.workDate }
                .forEach { record ->

                    val icon = when (record.workType) {
                        "水やり" -> "💧"
                        "肥料" -> "🌱"
                        "虫害" -> "🐛"
                        "葉枯れ" -> "🍂"
                        "収穫" -> "🥕"
                        else -> "📝"
                    }

                    Text("${record.workDate}　$icon ${record.workType}")
                }

            Button(
                onClick = {
                    currentVegetable?.let {
                        navController.navigate(
                            "vegetable_edit/${it.vegetableId}"
                        )
                    }
                }
            ) {
                Text("✏️ 作物情報を編集")
            }

            Button(
                onClick = {
                    showDeleteDialog = true
                }
            ) {
                Text("🗑️ この作物を削除する")
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