//画面遷移の司令塔
package com.yuzu0923.miniveggarden.navigation

// import
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yuzu0923.miniveggarden.data.local.DatabaseProvider
import com.yuzu0923.miniveggarden.ui.AppHeader
import com.yuzu0923.miniveggarden.ui.BottomNavigation
import com.yuzu0923.miniveggarden.ui.EditVegetableScreen
import com.yuzu0923.miniveggarden.ui.FertilizerScreen
import com.yuzu0923.miniveggarden.ui.HarvestScreen
import com.yuzu0923.miniveggarden.ui.HomeScreen
import com.yuzu0923.miniveggarden.ui.LeafWitherScreen
import com.yuzu0923.miniveggarden.ui.ListScreen
import com.yuzu0923.miniveggarden.ui.PestScreen
import com.yuzu0923.miniveggarden.ui.RecordScreen
import com.yuzu0923.miniveggarden.ui.SettingScreen
import com.yuzu0923.miniveggarden.ui.VegetableDetailScreen
import com.yuzu0923.miniveggarden.ui.WateringScreen
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModelFactory
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModel
import com.yuzu0923.miniveggarden.viewmodel.WorkRecordViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController() //画面移動管理のコントローラー
    val navBackStackEntry = navController.currentBackStackEntryAsState() //現在の画面を取得する処理
    val currentRoute = navBackStackEntry.value?.destination?.route

    // 変数の定義（共通）
    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val factory = VegetableViewModelFactory(
        database.vegetableDao()
    )
    val vegetableViewModel : VegetableViewModel =
        viewModel(
            factory = factory
        )
    // 変数の定義（作業記録）
    val workFactory = WorkRecordViewModelFactory(
        workRecordDao = database.workRecordDao()
    )
    val workRecordViewModel : WorkRecordViewModel =
        viewModel(
            factory = workFactory
        )

    val title = when (currentRoute) {
        "home" -> "ホーム"
        "record" -> "記録"
        "list" -> "一覧"
        "setting" -> "設定"
        else -> "MiniVegGarden"
    }

    // 画面全体の基本レイアウト
    Scaffold(
        topBar = { // ヘッダー
            AppHeader(
                title = title,
            )
        },
        bottomBar = { //画面下部
            BottomNavigation(navController)
        }
    ) { innerPadding ->
        NavHost( //画面遷移管理場所
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            //"home"という名前の画面に来たら、HomeScreen()を表示
            composable("home") {
                HomeScreen(
                    vegetableViewModel = vegetableViewModel,
                    workRecordViewModel = workRecordViewModel,
                    navController = navController
                )
            }

            //"record"という名前の画面に来たら、RecordScreen()を表示
            composable("record") {
                RecordScreen(
                    workRecordViewModel = workRecordViewModel,
                    vegetableViewModel = vegetableViewModel
                )
            }

            //"list"という名前の画面に来たら、ListScreen()を表示
            composable("list") {
                ListScreen(
                    vegetableViewModel = vegetableViewModel,
                    navController = navController)
            }

            //"setting"という名前の画面に来たら、SettingScreen()を表示
            composable("setting") {
                SettingScreen()
            }

            //VegetableIdをもとに作物を特定して、作物詳細を表示
            composable("vegetable_detail/{vegetableId}") { backStackEntry -> //この画面へのNavigation情報を backStackEntry という名前で受け取ります
                VegetableDetailScreen(
                    backStackEntry.arguments
                        ?.getString("vegetableId") //arguments がnullでない場合、getString()を実行
                        ?.toInt(), //getString()の結果がnullでない場合、toInt() を実行
                    vegetableViewModel = vegetableViewModel,
                    workRecordViewModel = workRecordViewModel,
                    navController = navController
                )
            }

            composable("vegetable_edit/{vegetableId}") { backStackEntry ->
                EditVegetableScreen(
                    vegetableId = backStackEntry.arguments
                        ?.getString("vegetableId")
                        ?.toInt(),
                    vegetableViewModel = vegetableViewModel,
                    navController = navController
                )
            }

            //作業内容選択画面を表示
            composable("work_type") {
                Column {
                    Text("作業の種類を選択")
                    // 選択肢「水やり」
                    Button(
                        onClick = {
                            navController.navigate("work_record")
                        }
                    ) {
                        Text("💧 水やり")
                    }

                    // 選択肢「肥料」
                    Button(
                        onClick = {
                            navController.navigate("fertilizer")
                        }
                    ) {
                        Text("🌱 肥料")
                    }

                    // 選択肢「虫害」
                    Button(
                        onClick = {
                            navController.navigate("pest")
                        }
                    ) {
                        Text("🐛 虫害")
                    }

                    // 選択肢「葉枯れ」
                    Button(
                        onClick = {
                            navController.navigate("leaf_wither")
                        }
                    ) {
                        Text("🍂 葉枯れ")
                    }

                    // 選択肢「水やり」
                    Button(
                        onClick = {
                            navController.navigate("harvest")
                        }
                    ) {
                        Text("🥕 収穫")
                    }

                    // 戻るボタン
                    Button(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("戻る")
                    }
                }
            } // 作業内容選択画面のcomposable終端

            // 水やりの記録画面
            composable("work_record") {
                WateringScreen(
                    vegetableViewModel = vegetableViewModel,
                    workRecordViewModel = workRecordViewModel,
                    navController = navController
                )
            }

            // 肥料の記録画面
            composable("fertilizer") {
                FertilizerScreen(
                    vegetableViewModel = vegetableViewModel,
                    workRecordViewModel = workRecordViewModel,
                    navController = navController
                )
            }

            // 虫害の記録画面
            composable("pest") {
                PestScreen(
                    vegetableViewModel = vegetableViewModel,
                    workRecordViewModel = workRecordViewModel,
                    navController = navController
                )
            }

            // 葉枯れの記録画面
            composable("leaf_wither") {
                LeafWitherScreen(
                    vegetableViewModel = vegetableViewModel,
                    workRecordViewModel = workRecordViewModel,
                    navController = navController
                )
            }

            // 収穫の記録画面
            composable("harvest") {
                HarvestScreen(
                    vegetableViewModel = vegetableViewModel,
                    workRecordViewModel = workRecordViewModel,
                    navController = navController
                )
            }
        }
    }
}