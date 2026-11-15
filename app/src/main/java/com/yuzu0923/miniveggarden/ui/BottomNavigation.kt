// 画面下部に表示するナビゲーションメニューを定義
package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import com.yuzu0923.miniveggarden.theme.GardenCream
import com.yuzu0923.miniveggarden.theme.GardenGreen

@Composable
fun BottomNavigation(
    navController: NavHostController
    ) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        // ホーム画面
        NavigationBarItem(
            selected = currentRoute == "home", //今いる画面がhomeなら、このボタンを選択状態にする
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            }, //home画面に移動する
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "ホーム"
                )
            },
            label = {
                Text("ホーム")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GardenGreen,
                selectedTextColor = GardenGreen,
                indicatorColor = GardenCream,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        // 一覧画面
        NavigationBarItem(
            selected = currentRoute == "list",
            onClick = {
                navController.navigate("list") {
                    popUpTo("home") {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "一覧"
                )
            },
            label = {
                Text("一覧")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GardenGreen,
                selectedTextColor = GardenGreen,
                indicatorColor = GardenCream,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        // 記録画面
        NavigationBarItem(
            selected = currentRoute == "record",
            onClick = {
                navController.navigate("record") {
                    popUpTo("home") {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "記録"
                )
            },
            label = {
                Text("記録")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GardenGreen,
                selectedTextColor = GardenGreen,
                indicatorColor = GardenCream,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        // 設定画面
        NavigationBarItem(
            selected = currentRoute == "setting",
            onClick = {
                navController.navigate("setting") {
                    popUpTo("home") {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "設定"
                )
            },
            label = {
                Text("設定")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GardenGreen,
                selectedTextColor = GardenGreen,
                indicatorColor = GardenCream,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}
