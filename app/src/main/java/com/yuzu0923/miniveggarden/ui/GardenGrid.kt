// 畑グリッドの生成・描写
package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yuzu0923.miniveggarden.data.local.VegetableEntity

@Composable
fun GardenGrid(
    vegetables: List<VegetableEntity>,
    onCellSelected: (Int, Int) -> Unit
) {
    // 変数の定義
    var selectedRow by remember { mutableStateOf<Int?>(null) }
    var selectedColumn by remember { mutableStateOf<Int?>(null) }

    // レイアウト
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // 列を５つ作成
        repeat(5) { row -> //row=0～4
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // 行を５つ作成
                repeat(5) { column ->
                    // 作物情報と連携させる
                    val vegetable = vegetables.find { //作物一覧から、行列が一致する作物を探す
                        it.row == row && it.col == column
                    }

                    // マスに情報を連携
                    GardenCell(
                        row = row,
                        column = column,
                        vegetable = vegetable,
                        isSelected = (
                                row == selectedRow &&
                                        column == selectedColumn
                                ),
                        onClick = { clickedRow, clickedColumn ->
                            selectedRow = clickedRow
                            selectedColumn = clickedColumn

                            onCellSelected(
                                clickedRow,
                                clickedColumn
                            )
                        }
                    )
                } // 行作成の終端
            }
        } // 列作成の終端
    } // Columnの終端
}