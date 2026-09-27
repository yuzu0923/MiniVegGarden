package com.yuzu0923.miniveggarden.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {

    @Insert //畑を保存する
    suspend fun insertField(vararg fields: FieldEntity)

    @Query("SELECT * FROM fields") //畑をすべて取得する
    fun getAllFields(): Flow<List<FieldEntity>>

}