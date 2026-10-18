// データベースを用意し、アプリ内で利用できるようにする
package com.yuzu0923.miniveggarden.data.local

// import
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider { // アプリ内で共有される一つのオブジェクト
    private var instance: AppDatabase? = null //【？】AppDatabase、またはnullが入る

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE vegetables ADD COLUMN iconName TEXT NOT NULL DEFAULT 'tomato'"
            )
        }
    }

    fun getDatabase(context: Context): AppDatabase {
        if (instance == null) {
            instance = Room.databaseBuilder( //インスタンスを生成
                context.applicationContext,
                AppDatabase::class.java,
                "mini_veg_garden_database"
            )
                .addMigrations(MIGRATION_1_2)
                .build()
        }
            return instance!! // 【!!】nullになりえない変数　既存のインスタンスを返す
    }
}
