package com.yuzu0923.miniveggarden.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api //エラー消しTopAppBar

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
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    )
}