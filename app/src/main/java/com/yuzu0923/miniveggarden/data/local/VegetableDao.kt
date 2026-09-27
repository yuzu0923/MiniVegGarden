package com.yuzu0923.miniveggarden.data.local
//野菜テーブルのDBに対して、データを出し入れするための窓口
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VegetableDao { //野菜を保存する,野菜を取得する
    @Insert //野菜を保存する
    suspend fun insertVegetable(vararg vegetables: VegetableEntity) //sus 時間がかかる可能性がある処理なので、特別な場所から実行してください

    @Query("SELECT * FROM vegetables") //野菜をすべて取得する
    fun getAllVegetables(): Flow<List<VegetableEntity>> //「今の一覧を一回読み取って終わり」ではなく、「DBの変化を監視して、変化があれば新しい一覧を流してくれるもの」

    @Query("SELECT * FROM vegetables WHERE vegetableId = :vegetableId")
    fun getVegetableById(vegetableId: Int): Flow<VegetableEntity?>

    @Delete
    suspend fun deleteVegetable(
        vegetable: VegetableEntity
    )

    @Update
    suspend fun updateVegetable(
        vegetable: VegetableEntity
    )
}