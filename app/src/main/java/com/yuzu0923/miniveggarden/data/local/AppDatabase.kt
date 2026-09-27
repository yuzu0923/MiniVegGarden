package com.yuzu0923.miniveggarden.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FieldEntity::class,
        VegetableEntity::class,
        WorkRecordEntity::class
        ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun fieldDao(): FieldDao
    abstract fun vegetableDao(): VegetableDao
    abstract fun workRecordDao(): WorkRecordDao
}

