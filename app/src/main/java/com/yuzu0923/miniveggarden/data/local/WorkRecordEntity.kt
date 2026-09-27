package com.yuzu0923.miniveggarden.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workRecords") //このクラスをRoomのテーブルとして扱う
data class WorkRecordEntity(

    @PrimaryKey(autoGenerate = true) //recordIdを主キーにして、番号は自動採番する
    val recordId: Int = 0,
    val vegetableId: Int,
    val workType: String,
    val workDate: String
)