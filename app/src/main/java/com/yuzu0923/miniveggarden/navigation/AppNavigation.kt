package com.yuzu0923.miniveggarden.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.ui.Modifier
import com.yuzu0923.miniveggarden.ui.HomeScreen
import com.yuzu0923.miniveggarden.ui.ListScreen
import com.yuzu0923.miniveggarden.ui.RecordScreen
import com.yuzu0923.miniveggarden.ui.SettingScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import com.yuzu0923.miniveggarden.ui.BottomNavigation

import androidx.navigation.compose.currentBackStackEntryAsState
import com.yuzu0923.miniveggarden.ui.AppHeader


//画面遷移の司令塔
@Composable
fun AppNavigation() {
    val navController = rememberNavController() //画面移動管理のコントローラー
    val navBackStackEntry = navController.currentBackStackEntryAsState() //現在の画面を取得する処理
    val currentRoute = navBackStackEntry.value?.destination?.route

    val title = when (currentRoute) {
        "home" -> "ホーム"
        "record" -> "記録"
        "list" -> "一覧"
        "setting" -> "設定"
        else -> "MiniVegGarden"
    }

    // 画面全体の基本レイアウト
    Scaffold( //画面のレイアウトを作成
        topBar = {
            AppHeader(
                title = title,
            )
        },
        bottomBar = { //画面下部の設定
            BottomNavigation(navController)
        }
    ) { innerPadding ->

        NavHost( //画面遷移管理場所
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("home") { //homeという名前の画面に来たら、HomeScreen()を表示
                HomeScreen()
            }

            composable("record") {
                RecordScreen()
            }

            composable("list") {
                ListScreen()
            }

            composable("setting") {
                SettingScreen()
            }
        }
    }
}