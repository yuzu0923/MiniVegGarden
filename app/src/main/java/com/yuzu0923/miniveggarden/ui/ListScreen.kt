package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel

@Composable //この関数は画面として描画できます
fun ListScreen(
    vegetableViewModel: VegetableViewModel, //funはPythonのdef
    navController: NavController
) {
    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }

    Column {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(vegetables) { vegetable ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate(
                                "vegetable_detail/${vegetable.vegetableId}"
                            )
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = vegetable.vegetableName
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${vegetable.row + 1}行目・${vegetable.col + 1}列目"
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                showAddDialog = true

            }
        ) {
            Text("新しい作物を植える")
        }

        if (showAddDialog) {
            AddVegetableDialog(
                row = null,
                column = null,
                vegetables = vegetables,
                vegetableViewModel = vegetableViewModel,
                onDismiss = {
                    showAddDialog = false
                }
            )
        }
    }
}
