package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel

@Composable
fun WorkRecordScreen(
    vegetableId: Int?,
    workRecordViewModel: WorkRecordViewModel,
    navController: NavController
) {

    Column {
        Text("作業記録")
        Text("対象の野菜ID：$vegetableId")

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("戻る")
        }


        Button(
            onClick = {
                navController.navigate("work_record/watering")
            }
        ) {
            Text("戻る")
        }


    }
}
