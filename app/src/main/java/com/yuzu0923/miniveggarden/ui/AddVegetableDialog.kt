package com.yuzu0923.miniveggarden.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yuzu0923.miniveggarden.data.local.VegetableEntity
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel

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

    var showFullFieldDialog by remember { // 畑が満杯の場合
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text("新規作物登録")
        },
        text = {
            OutlinedTextField(
                value = vegetableName,
                onValueChange = {
                    vegetableName = it
                    },
                label = {
                        Text("作物名")
                    }
                )
        },
        confirmButton = {
            TextButton(
                enabled = vegetableName.isNotBlank(),
                onClick = {
                    println("登録する作物：$vegetableName")

                    // 最終処理（row,colの確定値にどちらの値を使うか）
                    var registerRow = row // 確定列
                    var registerCol = column // 確定行

                    if (row != null && column != null) { // 行列の選択情報がある場合
                        println("登録先：${row + 1}行目・${column + 1}列目")
                    } else { // 行列の選択情報がない場合（一覧からの新規追加）

                        //左上から空いているマスを探して自動選択
                        var emptyRow: Int? = null
                        var emptyCol: Int? = null

                        search@ for (searchRow in 0..4) {
                            for (searchCol in 0..4) {
                                val isOccupied = vegetables.any { vegetable ->
                                    vegetable.row == searchRow && vegetable.col == searchCol
                                }

                                if (!isOccupied) {
                                    emptyRow = searchRow
                                    emptyCol = searchCol
                                    break@search // searchのタグが付いたfor文を抜け出す
                                }
                            }
                        }
                        registerRow = emptyRow
                        registerCol = emptyCol

                        if (emptyRow == null || emptyCol == null) {
                            showFullFieldDialog = true
                            return@TextButton //このボタンのクリック処理をここで終了する
                        }
                    }



                    // row / column → ダイアログに最初から渡された座標
                    // searchRow / searchCol → 今回の探索で調べている座標
                    if (registerRow != null && registerCol != null) {

                        val vegetable = VegetableEntity(
                            vegetableName = vegetableName,
                            row = registerRow,
                            col = registerCol,
                            fieldId = 1
                        )

                        vegetableViewModel.addVegetable(vegetable)

                        onDismiss()
                    }
                },
            ) {
                Text("登録")
            }
        },

        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("閉じる")
            }
        }
    )

    if (showFullFieldDialog) {
        AlertDialog(
            onDismissRequest = {
                showFullFieldDialog = false
            },
            title = {
                Text("畑がいっぱいです")
            },
            text = {
                Text("これ以上作物を登録できる空きマスがありません。")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showFullFieldDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}