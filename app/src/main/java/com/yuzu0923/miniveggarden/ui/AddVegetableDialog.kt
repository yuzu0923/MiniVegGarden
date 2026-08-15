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

@Composable
fun AddVegetableDialog(
    row: Int,
    column: Int,
    onDismiss: () -> Unit //引数３つ
) {

    // 作物名を一時的に保存する
    var vegetableName by remember {
        mutableStateOf("")
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
                onClick = {
                    println("登録する作物：$vegetableName")
                    println("登録先：${row + 1}行目・${column + 1}列目")
                }
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
}