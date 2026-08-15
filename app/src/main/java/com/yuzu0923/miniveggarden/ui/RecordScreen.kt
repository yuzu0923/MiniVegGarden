package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable

@Composable //この関数は画面として描画できます
fun RecordScreen() { //funはPythonのdef
    Box( //HTMLのdev
        modifier = Modifier.fillMaxSize(), //レイアウト設定（未）
        contentAlignment = Alignment.Center //真ん中に文字を表示
    ) {
        Text("農場の記録画面")
    }
}