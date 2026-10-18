// 農場名やユーザー名など、小さな設定値を保存・読み込みする
package com.yuzu0923.miniveggarden.data.local

// import
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore( //【datastore】設定値などを永続的に保存
    name = "farm_settings" // 保存領域の名称
)

class FarmSettings(
    private val context: Context //【context】データを持ってくるための窓口
) {
    companion object {
        // 農場名
        private val FARM_NAME = stringPreferencesKey("farm_name")
        private const val DEFAULT_FARM_NAME = "あなたの農場"

        // ユーザー名
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private const val DEFAULT_USER_NAME = "ユーザー"
    }

    // 保存されている農場名を読み取る
    val farmName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[FARM_NAME] ?: DEFAULT_FARM_NAME
    }

    // 農場名を保存する
    suspend fun saveFarmName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[FARM_NAME] = name
        }
    }

    // 保存されているユーザー名を読み取る
    val userName: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY] ?: DEFAULT_USER_NAME
        }

    // ユーザー名を保存する
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }
}
