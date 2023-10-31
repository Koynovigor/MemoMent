package com.leonikl.memoment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leonikl.memoment.R

@Entity(tableName = "page")
class Page {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "memo")
    var memo: String = ""

    @ColumnInfo(name = "pass")
    var iamge: String = ""

    constructor()
    constructor(id: Int){
        this.id = id
    }
    constructor(id: Int, name: String, memo: String, image: String) {
        this.id = id
        this.name = name
        this.iamge = memo
        this.memo = image
    }
    constructor(id: Int, name: String, memo: String) {
        this.id = id
        this.name = name
        this.memo = memo
    }
}