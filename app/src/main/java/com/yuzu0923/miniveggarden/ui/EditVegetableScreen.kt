package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@Composable
fun EditVegetableScreen(
    vegetableId: Int?,
    vegetableViewModel: VegetableViewModel,
    navController: NavController
) {
    val vegetable by vegetableViewModel
        .getVegetableById(vegetableId ?: -1)
        .collectAsState(initial = null)

    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())

    var vegetableName by remember { mutableStateOf("") }
    var selectedRow by remember { mutableStateOf(0) }
    var selectedColumn by remember { mutableStateOf(0) }

    var rowExpanded by remember { mutableStateOf(false) }
    var columnExpanded by remember { mutableStateOf(false) }

    // DBから作物情報を取得できたら、編集欄へ初期値を入れる
    LaunchedEffect(vegetable) {
        vegetable?.let {
            vegetableName = it.vegetableName
            selectedRow = it.row
            selectedColumn = it.col
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

            Text("場所")

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                // 行を選択
                Box {
                    Button(
                        onClick = { rowExpanded = true }
                    ) {
                        Text("${selectedRow + 1}行目")
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

                Spacer(modifier = Modifier.width(8.dp))

                // 列を選択
                Box {
                    Button(
                        onClick = { columnExpanded = true }
                    ) {
                        Text("${selectedColumn + 1}列目")
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

            if (isOccupied) {
                Text("この場所には別の作物が登録されています。")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("畑ID：${currentVegetable.fieldId}")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                enabled = vegetableName.isNotBlank() && !isOccupied,
                onClick = {
                    val updatedVegetable = currentVegetable.copy(
                        vegetableName = vegetableName.trim(),
                        row = selectedRow,
                        col = selectedColumn
                    )

                    vegetableViewModel.updateVegetable(updatedVegetable)

                    navController.popBackStack()
                }
            ) {
                Text("保存する")
            }

            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("キャンセル")
            }
        }
    }
}