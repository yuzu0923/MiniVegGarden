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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.yuzu0923.miniveggarden.ui.AddVegetableDialog

@Composable //この関数は画面として描画できます
fun HomeScreen() { //funはPythonのdef

    var selectedRow by remember { mutableStateOf<Int?>(null) }
    var selectedColumn by remember { mutableStateOf<Int?>(null) }
    var showAddVegetableDialog by remember {
        mutableStateOf(false) //小窓の表示ONOFF
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7CB342))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("畑A")

        GardenGrid(
            onCellSelected = { row, column ->
                selectedRow = row
                selectedColumn = column
            }
        )
    }

    if (selectedRow != null && selectedColumn != null) {
        Button(
            onClick = {
                showAddVegetableDialog = true
            }
        ) {
            Text("野菜を登録")
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
            onDismiss = {
                showAddVegetableDialog = false
            }
        )
    }








}