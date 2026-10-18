// アプリ画面上部に表示する共通ヘッダーを定義する
package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api //エラー消しTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import com.yuzu0923.miniveggarden.theme.GardenGreen
import com.yuzu0923.miniveggarden.theme.GardenYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    title: String
) {
    TopAppBar(
        title = {
            Row {
                Text(
                    text = "●",
                    color = GardenYellow
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = GardenGreen,
            titleContentColor = Color.White
        )
    )
}
