package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.data.local.WorkRecordEntity

import com.yuzu0923.miniveggarden.ui.AddVegetableDialog
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.material3.AlertDialog

@Composable
fun HomeScreen(
    vegetableViewModel: VegetableViewModel,
    workRecordViewModel: WorkRecordViewModel,
    navController: NavController
) {
    // 変数の定義
    var selectedRow by remember { mutableStateOf<Int?>(null) } //選択行
    var selectedColumn by remember { mutableStateOf<Int?>(null) } //選択列
    var showAddVegetableDialog by remember {
        mutableStateOf(false) //作物の新規追加小窓
    }
    var showVegetableDetail by remember {
        mutableStateOf(false) //作物の詳細確認小窓
    }
    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList()) //初期値は空リスト
    var showWateringDialog by remember {
        mutableStateOf(false) //水やりボタン表示
    }

    // 配置
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7CB342)) //畑の草地の表示
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("畑A")

        GardenGrid(
            vegetables = vegetables,
            onCellSelected = { row, column ->
                selectedRow = row
                selectedColumn = column

                val vegetable = vegetables.find { //行・列が一致する野菜を見つけて変数に代入
                    it.row == row && it.col == column
                }

                // マスを選択した際のボタン表示分岐
                if (vegetable != null) { // 選択マスに野菜の登録がある場合
                    showVegetableDetail = true // 詳細表示のボタンを表示
                } else { // 選択マスに野菜の登録がない場合
                    showVegetableDetail = false // 詳細表示のボタンを表示しない
                }
            }
        )

        // ■「作業記録をつける」ボタン
        Button(
            onClick = {
                navController.navigate("work_type")
            }
        ) {
            Text("📝 作業記録をつける")
        }

        // ■「すべての作物に水やりをする」ボタン
        Button(
            onClick = {
                showWateringDialog = true
            }
        ) {
            Text("💧 すべての作物に水やりをする")
        }

        // ■「作業記録を見る」ボタン
        Button(
            onClick = {
                navController.navigate("record")
            }
        ) {
            Text("📖 作業記録を見る")
        }
    } // Column終端

    // 選択マスがない場合
    if (selectedRow != null && selectedColumn != null) {
        Button(
            onClick = {
                if (showVegetableDetail) {
                    val selectedVegetable = vegetables.find {
                        it.row == selectedRow && it.col == selectedColumn
                    }

                    if (selectedVegetable != null) {
                        navController.navigate(
                            "vegetable_detail/${selectedVegetable.vegetableId}"
                        )
                    }
                } else {
                    showAddVegetableDialog = true
                }
            }
        ) {
            // 野菜の登録有無によるボタンの表示名を分岐
            if (showVegetableDetail) {
                Text("詳細を確認")
            } else {
                Text("野菜を登録")
            }
        }
    }

    if (
        showAddVegetableDialog &&
        selectedRow != null &&
        selectedColumn != null
    ) {
        AddVegetableDialog(
            row = selectedRow!!,
            column = selectedColumn!!,
            vegetables = vegetables,
            vegetableViewModel = vegetableViewModel,
            onDismiss = {
                showAddVegetableDialog = false
            }
        )
    }

    if (showWateringDialog) {
        AlertDialog(
            onDismissRequest = {
                showWateringDialog = false
            },
            title = {
                Text("水やりの確認")
            },
            text = {
                Text("すべての作物に水やりの記録をします")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val workRecords = vegetables.map { vegetable ->
                            WorkRecordEntity(
                                vegetableId = vegetable.vegetableId,
                                workType = "水やり",
                                workDate = LocalDate.now(
                                    ZoneId.of("Asia/Tokyo")
                                ).toString()
                            )
                        }
                        workRecordViewModel.addWorkRecords(workRecords)
                        showWateringDialog = false
                    }
                ) {
                    Text("はい")
                }
            },
            dismissButton = { // 戻るボタン
                TextButton(
                    onClick = {
                        showWateringDialog = false
                    }
                ) {
                    Text("やめる")
                }
            }
        )
    } // if文終端
} // 関数終端