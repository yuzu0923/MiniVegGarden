//野菜テーブルのDBに対して、データを出し入れするための窓口
package com.yuzu0923.miniveggarden.data.local

// import
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VegetableDao { //作物情報を保存、取得する
    // 作物情報を保存する
    @Insert
    suspend fun insertVegetable(vararg vegetables: VegetableEntity) // 【suspend】時間がかかる可能性がある処理 特別な場所から実行

    // 作物情報をすべて取得する
    @Query("SELECT * FROM vegetables")
    fun getAllVegetables(): Flow<List<VegetableEntity>> //DBの変化を監視して、変化があれば新しい一覧を流す

    // vegetableIdに基づいて作物情報を１件取得する
    @Query("SELECT * FROM vegetables WHERE vegetableId = :vegetableId")
    fun getVegetableById(vegetableId: Int): Flow<VegetableEntity?>

    // 作物情報を更新する
    @Update
    suspend fun updateVegetable(
        vegetable: VegetableEntity
    )

    // 作物情報を削除する
    @Delete
    suspend fun deleteVegetable(
        vegetable: VegetableEntity
    )
}