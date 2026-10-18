package com.yuzu0923.miniveggarden.ui

// import
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yuzu0923.miniveggarden.viewmodel.VegetableViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.yuzu0923.miniveggarden.R
import com.yuzu0923.miniveggarden.data.local.VegetableEntity

@Composable //この関数は画面として描画
fun ListScreen(
    vegetableViewModel: VegetableViewModel,
    navController: NavController
) {
    val vegetables by vegetableViewModel.vegetables
        .collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }

    val sortedVegetables = vegetables.sortedWith( //並び替え
        compareBy<VegetableEntity> { it.row }
            .thenBy { it.col }
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //「新しい作物を植える」ボタン
        Button(
            onClick = {
                showAddDialog = true
            },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(56.dp)
        ) {
            Text(
                text = "新しい作物を植える",
                fontSize = 17.sp
            )
        }

        if (vegetables.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("まだ作物が登録されていません。")
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(sortedVegetables) { vegetable ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate(
                                    "vegetable_detail/${vegetable.vegetableId}"
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 左側に作物のドット絵
                            val iconRes = when (vegetable.iconName) {
                                "tomato" -> R.drawable.tomato
                                "carrot" -> R.drawable.carrot
                                "pumpkin" -> R.drawable.pumpkin
                                else -> R.drawable.tomato
                            }

                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = vegetable.vegetableName,
                                modifier = Modifier.size(56.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // 作物名、番地を縦に表示
                            Column {
                                Text(
                                    text = vegetable.vegetableName,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "${vegetable.row + 1}行目・${vegetable.col + 1}列目",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AddVegetableDialog(
                row = null,
                column = null,
                vegetables = vegetables,
                vegetableViewModel = vegetableViewModel,
                onDismiss = {
                    showAddDialog = false
                }
            )
        }
    }