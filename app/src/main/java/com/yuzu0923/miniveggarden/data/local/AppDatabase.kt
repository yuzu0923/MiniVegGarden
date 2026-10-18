// アプリが使うデータベースの構造と、データへのアクセス窓口をまとめるクラス
package com.yuzu0923.miniveggarden.data.local

// import
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FieldEntity::class,
        VegetableEntity::class,
        WorkRecordEntity::class
        ],
    version = 2
)
// DAOの取得（Roomがこの宣言をもとに実装を生成）
abstract class AppDatabase : RoomDatabase(){
    abstract fun fieldDao(): FieldDao
    abstract fun vegetableDao(): VegetableDao
    abstract fun workRecordDao(): WorkRecordDao
}

