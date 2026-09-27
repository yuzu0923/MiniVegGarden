package com.yuzu0923.miniveggarden.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("設定")

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text("MiniVegGarden")

        Text("家庭菜園の作物と作業を記録するアプリ")

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text("畑のサイズ：5 × 5")

        Text("バージョン：1.0")
    }
}