package com.yuzu0923.miniveggarden.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider { //アプリ全体で1つだけ存在するもの
    private var instance: AppDatabase? = null //AppDatabase、またはnullを入れられる
    fun getDatabase(context: Context): AppDatabase {
        if (instance == null) {
            instance = Room.databaseBuilder( //ローカルデータベースのインスタンスを生成するために使用
                context.applicationContext,
                AppDatabase::class.java,
                "mini_veg_garden_database"
            ).build()
        }
            return instance!! //nullになりうる変数を「絶対にnullではない」として扱う書き方
    }
}
