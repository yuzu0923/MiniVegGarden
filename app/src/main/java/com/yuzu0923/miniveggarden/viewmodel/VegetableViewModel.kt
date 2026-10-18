//画面とデータベースの仲介役
package com.yuzu0923.miniveggarden.viewmodel

// import
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuzu0923.miniveggarden.data.local.VegetableDao
import com.yuzu0923.miniveggarden.data.local.VegetableEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class VegetableViewModel(
    private val vegetableDao: VegetableDao
) : ViewModel() {

    // クラス変数・クラス定数
    val vegetables = vegetableDao.getAllVegetables()

    // 野菜データを一つ追加する関数
    fun addVegetable(
        vegetable: VegetableEntity //VegetableEntityのデータを変数に格納
    ) {
        viewModelScope.launch { //suspend funを実行できる場所
            vegetableDao.insertVegetable(vegetable) //Dao内の関数insertvegitableを呼ぶ
        }
    }

    // あるIDをもつ作物の情報を取得して返す
    fun getVegetableById(vegetableId: Int): Flow<VegetableEntity?> {
        return vegetableDao.getVegetableById(vegetableId)
    }

    // 作物情報を更新する
    fun updateVegetable(vegetable: VegetableEntity) {
        viewModelScope.launch {
            vegetableDao.updateVegetable(vegetable)
        }
    }

    // 作物の情報を削除する
    fun deleteVegetable(
        vegetable: VegetableEntity
    ) {
        viewModelScope.launch {
            vegetableDao.deleteVegetable(vegetable)
        }
    }
}