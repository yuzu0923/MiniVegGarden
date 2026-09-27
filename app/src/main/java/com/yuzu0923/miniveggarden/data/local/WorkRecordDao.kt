package com.yuzu0923.miniveggarden.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkRecordDao {
    @Insert //作業を保存する
    suspend fun insertWorkRecord(vararg workRecords: WorkRecordEntity)

    @Query("SELECT * FROM workRecords") //作業をすべて取得する
    fun getAllWorkRecords(): Flow<List<WorkRecordEntity>>

    @Query("SELECT * FROM workRecords WHERE recordId = :recordId")
    fun getWorkRecordById(recordId: Int): Flow<WorkRecordEntity?>

    @Query("SELECT * FROM workRecords WHERE workDate = :workDate")
    fun getWorkRecordsByDate(workDate: String): Flow<List<WorkRecordEntity>>

    //「この作物・この作業・この日付の記録が、すでに存在するか
    @Query("""
    SELECT * FROM workRecords
    WHERE vegetableId = :vegetableId
    AND workType = :workType
    AND workDate = :workDate
    LIMIT 1
""")
    suspend fun getWorkRecord(
        vegetableId: Int,
        workType: String,
        workDate: String
    ): WorkRecordEntity?

    // ある作物のデータをすべて取得する
    @Query("SELECT * FROM workRecords WHERE vegetableId = :vegetableId")
    fun getWorkRecordsByVegetableId(
        vegetableId: Int
    ): Flow<List<WorkRecordEntity>>



    //「vegetableId が指定したIDの作業記録を全部削除する」
    @Query("DELETE FROM workRecords WHERE vegetableId = :vegetableId")
    suspend fun deleteWorkRecordsByVegetableId(
        vegetableId: Int
    )

}