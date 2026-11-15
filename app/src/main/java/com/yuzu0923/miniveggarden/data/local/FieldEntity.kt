// データベースに保存する畑の情報と、その構造を定義するクラス
package com.yuzu0923.miniveggarden.data.local

// import
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields") //このクラスをRoomのテーブルとして扱う
data class FieldEntity(

    @PrimaryKey(autoGenerate = true) //fieldIdを主キーにして、番号は自動採番する
    val fieldId: Int = 0,
    val fieldName: String?,
    val rows: Int,
    val cols: Int,
    val location: String?
)