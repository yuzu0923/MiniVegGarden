// 起動時に最初に開くホーム画面
package com.yuzu0923.miniveggarden.ui
// import
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.data.local.WorkRecordEntity
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp
import com.yuzu0923.miniveggarden.data.local.FarmSettings
import com.yuzu0923.miniveggarden.theme.GardenGrass


@Composable
fun HomeScreen(
    vegetableViewModel: VegetableViewModel,
    workRecordViewModel: WorkRecordViewModel,
    navController: NavController,
    farmSettings: FarmSettings
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
    val farmName by farmSettings.farmName.collectAsState(
        initial = "あなたの農場"
    )

    // 配置
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) //畑の草地の表示
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(farmName)

        Box(
            modifier = Modifier
                .background(
                    color = GardenGrass,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(24.dp)
        ) {
            GardenGrid(
                vegetables = vegetables,
                onCellSelected = { row, column ->
                    selectedRow = row
                    selectedColumn = column

                    val vegetable = vegetables.find { //行・列が一致する野菜を見つけて変数に代入
                        it.row == row && it.col == column
                    }

                    showVegetableDetail = vegetable != null
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            if (selectedRow != null && selectedColumn != null) {
                // 「詳細を確認」「作物を登録」ボタン
                Button(
                    onClick = {
                        if (showVegetableDetail) {
                            val selectedVegetable = vegetables.find {
                                it.row == selectedRow &&
                                        it.col == selectedColumn
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
                    if (showVegetableDetail) {
                        Text(
                            text = "詳細を確認",
                            fontSize = 17.sp
                        )
                    } else {
                        Text(
                            text = "作物を登録",
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }

            Spacer(modifier = Modifier.height(8.dp))

            // 「作業記録をつける」ボタン
            Button(
                onClick = { navController.navigate("work_type") },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(56.dp)
            ) {
                Text(
                    text = "📝 作業記録をつける",
                    fontSize = 17.sp
                )
            }

            // 「すべての作物に水やりをする」ボタン
            Button(
                onClick = { showWateringDialog = true },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(56.dp)
            ) {
                Text(
                    text = "💧 すべての作物に水やりをする",
                    fontSize = 17.sp
                )
            }

            // 「作業記録を見る」ボタン
            Button(
                onClick = { navController.navigate("record") },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(56.dp)
            ) {
                Text(
                    text = "📖 作業記録を見る",
                    fontSize = 17.sp
                )
            }
        } // Column終端

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
