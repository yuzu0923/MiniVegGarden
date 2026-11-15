// 野菜を新しく登録するためのダイアログを定義する
package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yuzu0923.miniveggarden.data.local.VegetableEntity
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yuzu0923.miniveggarden.R

@Composable
fun AddVegetableDialog(
    row: Int?,
    column: Int?,
    vegetables: List<VegetableEntity>,
    vegetableViewModel: VegetableViewModel,
    onDismiss: () -> Unit
) {
    // 変数の定義
    var vegetableName by remember { // 作物名を一時的に保存する
        mutableStateOf("")
    }

    // 選択中のアイコン名
    var selectedIconName by remember { mutableStateOf("tomato") }

    var showFullFieldDialog by remember { // 畑が満杯の場合
        mutableStateOf(false)
    }

    // アイコン名と画像リソースの対応表
    val vegetableIcons = listOf(
        "tomato" to R.drawable.tomato,
        "carrot" to R.drawable.carrot,
        "pumpkin" to R.drawable.pumpkin
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("新規作物登録") },
        text = {
            Column {
                OutlinedTextField(
                    value = vegetableName,
                    onValueChange = { vegetableName = it },
                    label = { Text("作物名") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("アイコンを選択")

                // 3種類のアイコンを横並びにする
                Row {
                    vegetableIcons.forEach { (iconName, iconRes) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedIconName = iconName
                                }
                                .padding(4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = iconName,
                                modifier = Modifier.size(56.dp)
                            )

                            Text(
                                text = when (iconName) {
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
            } },
        confirmButton = {
            TextButton(
                enabled = vegetableName.isNotBlank(),
                onClick = {
                    var registerRow = row
                    var registerCol = column

                    // 場所が指定されていない場合、空きマスを探す
                    if (registerRow == null || registerCol == null) {
                        val emptyPosition = (0..4).asSequence()
                            .flatMap { searchRow ->
                                (0..4).asSequence().map { searchCol ->
                                    searchRow to searchCol
                                }
                            }
                            .firstOrNull { (searchRow, searchCol) ->
                                vegetables.none { vegetable ->
                                    vegetable.row == searchRow &&
                                            vegetable.col == searchCol
                                }
                            }

                        // 空きマスがなければ警告を表示
                        if (emptyPosition == null) {
                            showFullFieldDialog = true
                            return@TextButton
                        }

                        registerRow = emptyPosition.first
                        registerCol = emptyPosition.second
                    }

                    // 登録する作物を作成
                    val vegetable = VegetableEntity(
                        vegetableName = vegetableName.trim(),
                        row = registerRow,
                        col = registerCol,
                        fieldId = 1,
                        iconName = selectedIconName
                    )

                    vegetableViewModel.addVegetable(vegetable)
                    onDismiss()
                }
            ) {
                Text("登録")
            } },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("閉じる")
            }
        }
    )

        // 畑が満杯の場合の警告
        if (showFullFieldDialog) {
            AlertDialog(
                onDismissRequest = { showFullFieldDialog = false },
                title = { Text("畑がいっぱいです") },
                text = {
                    Text("これ以上作物を登録できる空きマスがありません。")
                },
                confirmButton = {
                    TextButton(onClick = { showFullFieldDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
