package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GardenCell(
    row: Int,
    column: Int,
    isSelected: Boolean,
    onClick: (Int, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(
                if (isSelected) {
                    Color(0xFFFFD54F)
                } else {
                    Color(0xFF8B5A2B)
                }
            )
            .clickable {
                onClick(row, column)
            }
    )
}