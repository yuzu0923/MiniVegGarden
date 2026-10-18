// 登録済みの作物情報を編集する画面
package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.yuzu0923.miniveggarden.R

@Composable
fun EditVegetableScreen(
    vegetableId: Int?,
    vegetableViewModel: VegetableViewModel,
    navController: NavController
) {
    val vegetable by vegetableViewModel
        .getVegetableById(vegetableId ?: -1) // vegetableIdがnull だったときに代わりに-1を代入
        .collectAsState(initial = null)

    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())

    var vegetableName by remember { mutableStateOf("") }
    var selectedRow by remember { mutableIntStateOf(0) }
    var selectedColumn by remember { mutableIntStateOf(0) }
    var rowExpanded by remember { mutableStateOf(false) }
    var columnExpanded by remember { mutableStateOf(false) }
    var selectedIconName by remember { mutableStateOf("tomato") }

    // DBから作物情報を取得できたら、編集欄へ初期値を入れる
    LaunchedEffect(vegetable) {
        vegetable?.let {
            vegetableName = it.vegetableName
            selectedRow = it.row
            selectedColumn = it.col
            selectedIconName = it.iconName
        }
    }
    val currentVegetable = vegetable

    if (currentVegetable != null) {
        val isOccupied = vegetables.any {
            it.vegetableId != currentVegetable.vegetableId &&
                    it.row == selectedRow &&
                    it.col == selectedColumn
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("作物情報を編集")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = vegetableName,
                onValueChange = { vegetableName = it },
                label = { Text("作物名") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("アイコンを選択")

            val vegetableIcons = listOf(
                "tomato" to R.drawable.tomato,
                "carrot" to R.drawable.carrot,
                "pumpkin" to R.drawable.pumpkin
            )

            Row {
                vegetableIcons.forEach { (iconName, iconRes) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedIconName = iconName }
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = iconName,
                            modifier = Modifier.size(56.dp)
                        )

                        Text(
                            when (iconName) {
                                "tomato" -> "トマト"
                                "carrot" -> "ニンジン"
                                "pumpkin" -> "カボチャ"
                                else -> iconName
                            }
                        )

                        RadioButton(
                            selected = selectedIconName == iconName,
                            onClick = { selectedIconName = iconName }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("場所")
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 行を選択
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { rowExpanded = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${selectedRow + 1}行目",
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "行を選択"
                        )
                    }

                    DropdownMenu(
                        expanded = rowExpanded,
                        onDismissRequest = { rowExpanded = false }
                    ) {
                        repeat(5) { row ->
                            DropdownMenuItem(
                                text = { Text("${row + 1}行目") },
                                onClick = {
                                    selectedRow = row
                                    rowExpanded = false
                                }
                            )
                        }
                    }
                }

                // 列を選択
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { columnExpanded = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${selectedColumn + 1}列目",
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "列を選択"
                        )
                    }

                    DropdownMenu(
                        expanded = columnExpanded,
                        onDismissRequest = { columnExpanded = false }
                    ) {
                        repeat(5) { column ->
                            DropdownMenuItem(
                                text = { Text("${column + 1}列目") },
                                onClick = {
                                    selectedColumn = column
                                    columnExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // 指定の場所に既に作物が登録されている場合
            if (isOccupied) {
                Text("この場所には別の作物が登録されています。")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("畑ID：${currentVegetable.fieldId}")
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    enabled = vegetableName.isNotBlank() && !isOccupied,
                    onClick = {
                        val updatedVegetable = currentVegetable.copy(
                            vegetableName = vegetableName.trim(),
                            row = selectedRow,
                            col = selectedColumn,
                            iconName = selectedIconName
                        )
                        vegetableViewModel.updateVegetable(updatedVegetable)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp)
                ) {
                    Text("保存する", fontSize = 17.sp)
                }

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp)
                ) {
                    Text("キャンセル", fontSize = 17.sp)
                }
            }
        }
    }
}
