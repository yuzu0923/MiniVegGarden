package com.yuzu0923.miniveggarden.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuzu0923.miniveggarden.data.local.VegetableDao
import com.yuzu0923.miniveggarden.data.local.VegetableEntity
import com.yuzu0923.miniveggarden.data.local.WorkRecordDao
import com.yuzu0923.miniveggarden.data.local.WorkRecordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WorkRecordViewModel (
    private val workRecordDao: WorkRecordDao //workRecordDaoの関数
) : ViewModel() {

    val workRecords = workRecordDao.getAllWorkRecords()

    // 作業データを1件追加する
    fun addWorkRecord(
        workRecord: WorkRecordEntity //変数に、WorkRecordEntityのとおりにデータを格納する
    ) {
        viewModelScope.launch { //suspend funを実行できる場所
            workRecordDao.insertWorkRecord(workRecord)
        }
    }

    // 作業データを複数追加する+同じ記録がないかどうか探して重複を防ぐ
    fun addWorkRecords(
        workRecords: List<WorkRecordEntity>
    ) {
        viewModelScope.launch {
            workRecords.forEach { workRecord ->

                val existingRecord = workRecordDao.getWorkRecord(
                    vegetableId = workRecord.vegetableId,
                    workType = workRecord.workType,
                    workDate = workRecord.workDate
                )

                if (existingRecord == null) {
                    workRecordDao.insertWorkRecord(workRecord)
                }
            }
        }
    }

    fun getWorkRecordById(recordId: Int): Flow<WorkRecordEntity?> {
        return workRecordDao.getWorkRecordById(recordId)
    }


    fun getWorkRecordsByDate(
        workDate: String
    ): Flow<List<WorkRecordEntity>> {
        return workRecordDao.getWorkRecordsByDate(workDate)
    }


    fun getWorkRecordsByVegetableId(
        vegetableId: Int
    ): Flow<List<WorkRecordEntity>> {
        return workRecordDao.getWorkRecordsByVegetableId(vegetableId)
    }

    fun deleteWorkRecordsByVegetableId(
        vegetableId: Int
    ) {
        viewModelScope.launch {
            workRecordDao.deleteWorkRecordsByVegetableId(vegetableId)
        }
    }


}