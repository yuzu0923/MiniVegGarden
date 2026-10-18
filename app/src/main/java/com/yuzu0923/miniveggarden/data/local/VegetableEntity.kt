// データベースに保存する作物の情報と、その構造を定義するクラス
package com.yuzu0923.miniveggarden.data.local
// import
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@ColumnInfo(defaultValue = "'tomato'")
val iconName: String = "tomato"

@Entity(tableName = "vegetables") //このクラスをRoomのテーブルとして扱う
data class VegetableEntity(
    @PrimaryKey(autoGenerate = true) //vegetableIdを主キーにして、番号は自動採番する
    val vegetableId: Int = 0,
    val vegetableName: String,
    val row: Int,
    val col: Int,
    val fieldId: Int,
    val iconName: String = "tomato"
)