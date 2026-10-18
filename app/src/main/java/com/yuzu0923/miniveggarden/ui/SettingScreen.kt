// 設定を行う画面
package com.yuzu0923.miniveggarden.ui
// import
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import com.yuzu0923.miniveggarden.data.local.FarmSettings
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SettingScreen(
    farmSettings: FarmSettings
) {
    // 農場名に関する変数・定数
    val farmName by farmSettings.farmName.collectAsState(
        initial = "あなたの農場"
    )
    var editedFarmName by remember(farmName) {
        mutableStateOf(farmName)
    }
    var showEditDialog by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    // ユーザー名に関する変数・定数
    val userName by farmSettings.userName.collectAsState(
        initial = "ユーザー"
    )
    var editedUserName by remember(userName) {
        mutableStateOf(userName)
    }
    var showUserNameEditDialog by remember{
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "■ 基本情報",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 22.sp
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "農場名",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = farmName,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }

                    // 「変更」ボタン
                    TextButton(
                        onClick = {
                            editedFarmName = farmName
                            showEditDialog = true
                        }
                    ) {
                        Text("変更")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                        Text(
                            text = "ユーザー名",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }

                    // 「変更」ボタン
                    TextButton(
                        onClick = {
                            editedUserName = userName
                            showUserNameEditDialog = true
                        }
                    ) {
                        Text("変更")
                    }
                }

            }
        }

        Text(
            text = "■ アプリについて",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 22.sp
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "MiniVegGarden",
                    fontWeight = FontWeight.Bold)
                Text("家庭菜園の作物と作業を記録するアプリ")

                Text(
                    text = "バージョン：1.0"
                )
            }
        }

        Text(
            text = "■ 畑の設定",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 22.sp
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("畑のサイズ：5 × 5")
            }
        }
    }
    // 農場名変更の小窓
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
            },
            title = {
                Text("農場名の変更")
            },
            text = {
                OutlinedTextField(
                    value = editedFarmName,
                    onValueChange = {
                        editedFarmName = it
                    },
                    label = {
                        Text("農場名")
                    },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    enabled = editedFarmName.isNotBlank(),
                    onClick = {
                        val name = editedFarmName.trim()
                        scope.launch {
                            farmSettings.saveFarmName(name)
                            showEditDialog = false
                        }
                    }
                ) {
                    Text("保存")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEditDialog = false
                    }
                ) {
                    Text("キャンセル")
                }
            }
        )
    }

    // ユーザー名変更の小窓
    if (showUserNameEditDialog) {
        AlertDialog(
            onDismissRequest = {
                showUserNameEditDialog = false
            },
            title = {
                Text("ユーザー名の変更")
            },
            text = {
                OutlinedTextField(
                    value = editedUserName,
                    onValueChange = {
                        editedUserName = it
                    },
                    label = {
                        Text("ユーザー名")
                    },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    enabled = editedUserName.isNotBlank(),
                    onClick = {
                        val name = editedUserName.trim()
                        scope.launch {
                            farmSettings.saveUserName(name)
                            showUserNameEditDialog = false
                        }
                    }
                ) {
                    Text("保存")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showUserNameEditDialog = false
                    }
                ) {
                    Text("キャンセル")
                }
            }
        )
    }
}
