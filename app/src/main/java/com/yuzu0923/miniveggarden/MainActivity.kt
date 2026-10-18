// アプリを起動したときの入口
package com.yuzu0923.miniveggarden
// import
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yuzu0923.miniveggarden.theme.MiniVegGardenTheme
import com.yuzu0923.miniveggarden.navigation.AppNavigation

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() //アプリが起動するとき、OS標準のスプラッシュ画面を使う

        super.onCreate(savedInstanceState)

        setContent {
            MiniVegGardenTheme {
                AppNavigation()
            }
        }
    }
}