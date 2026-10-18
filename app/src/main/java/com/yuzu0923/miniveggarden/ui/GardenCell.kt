// 畑マスの生成・描写をする
package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yuzu0923.miniveggarden.data.local.VegetableEntity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import com.yuzu0923.miniveggarden.R

@Composable
fun GardenCell(
    row: Int,
    column: Int,
    vegetable: VegetableEntity?,
    isSelected: Boolean, // このセルが現在選択されているか
    onClick: (Int, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(
                if (isSelected) {
                    Color(0xFFFFD54F) //選択セルの色（黄色）
                } else {
                    Color(0xFF8B5A2B) //通常セルの色（茶色）
                },
                shape = RoundedCornerShape(6.dp)
            )
            .clickable {
                onClick(row, column)
            }
    ) {

        // セルに作物が登録されている場合、画像を表示する
        if (vegetable != null) {
            val iconRes = when (vegetable.iconName) {
                "tomato" -> R.drawable.tomato
                "carrot" -> R.drawable.carrot
                "pumpkin" -> R.drawable.pumpkin
                else -> R.drawable.tomato
            }

            val iconDescription = when (vegetable.iconName) {
                "tomato" -> "トマト"
                "carrot" -> "ニンジン"
                "pumpkin" -> "カボチャ"
                else -> vegetable.vegetableName
            }

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = iconDescription,
                modifier = Modifier
                    .fillMaxSize(0.95f)
                    .align(Alignment.Center)
            )
        }
    }
}