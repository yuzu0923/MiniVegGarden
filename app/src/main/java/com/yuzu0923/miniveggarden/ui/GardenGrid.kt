package com.yuzu0923.miniveggarden.ui

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

@Composable
fun GardenGrid(
    onCellSelected: (Int, Int) -> Unit
) {

    var selectedRow by remember { mutableStateOf<Int?>(null) } //画面の状態を覚えておいて、状態が変わったら画面を描き直して
    var selectedColumn by remember { mutableStateOf<Int?>(null) }

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        repeat(5) { row -> //row=0～5

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                repeat(5) { column ->

                    GardenCell(
                        row = row,
                        column = column,
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
                }
            }
        }
    }
}