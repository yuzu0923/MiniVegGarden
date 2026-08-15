package com.yuzu0923.miniveggarden.ui

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(
    navController: NavHostController
    )  {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == "home", //今いる画面がhomeなら、このボタンを選択状態にする
            onClick = {
                navController.navigate("home"){
                    popUpTo(navController.graph.startDestinationId) { //ここまで戻れるようにして、それより前の不要な履歴を整理する
                        saveState = true //画面を切り替えるとき、状態を保存しておきます。
                    }
                    launchSingleTop = true //同じ画面を何度も開こうとしたとき、履歴を積み重ねない
                    restoreState = true //保存しておいた状態があれば、戻ってきたときに復元
                }
            }, //home画面に移動する
            icon = {},
            label = {
                Text("ホーム")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "record",
            onClick = {
                navController.navigate("record"){
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {},
            label = {
                Text("記録")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "list",
            onClick = {
                navController.navigate("list"){
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {},
            label = {
                Text("一覧")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "setting",
            onClick = {
                navController.navigate("setting"){
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {},
            label = {
                Text("設定")
            }
        )
    }
}