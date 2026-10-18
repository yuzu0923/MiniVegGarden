// 作業データをデータベースに登録・取得するための処理を定義するインターフェース
package com.yuzu0923.miniveggarden.data.local
// import
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkRecordDao {
    // 作業記録を保存する
    @Insert
    suspend fun insertWorkRecord(vararg workRecords: WorkRecordEntity)

    // 作業記録をすべて取得する
    @Query("SELECT * FROM workRecords")
    fun getAllWorkRecords(): Flow<List<WorkRecordEntity>>

    // recordIdをもとに作業を1件取得する
    @Query("SELECT * FROM workRecords WHERE recordId = :recordId")
    fun getWorkRecordById(recordId: Int): Flow<WorkRecordEntity?>

    // workdateをもとに作業を取得する
    @Query("SELECT * FROM workRecords WHERE workDate = :workDate")
    fun getWorkRecordsByDate(workDate: String): Flow<List<WorkRecordEntity>>

    //「この作物・この作業・この日付の記録が、すでに存在するか検証する
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

    // vegetableIdをもとに、その作物に関わる作業記録をすべて取得する
    @Query("SELECT * FROM workRecords WHERE vegetableId = :vegetableId")
    fun getWorkRecordsByVegetableId(
        vegetableId: Int
    ): Flow<List<WorkRecordEntity>>

    // vegetableIdをもとに、そのIDの作業記録を全部削除する
    @Query("DELETE FROM workRecords WHERE vegetableId = :vegetableId")
    suspend fun deleteWorkRecordsByVegetableId(
        vegetableId: Int
    )
}
