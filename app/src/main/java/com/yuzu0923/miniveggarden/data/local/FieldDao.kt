// 畑のデータをデータベースに登録・取得するための処理を定義するインターフェース
package com.yuzu0923.miniveggarden.data.local

// import
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {
    // 畑情報を保存する
    @Insert
    suspend fun insertField(vararg fields: FieldEntity)

    // 畑情報をすべて取得する
    @Query("SELECT * FROM fields")
    fun getAllFields(): Flow<List<FieldEntity>>
}