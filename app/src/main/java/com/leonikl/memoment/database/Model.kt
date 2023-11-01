package com.leonikl.memoment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page")
class Page {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "memo")
    var memo: String = ""

    @ColumnInfo(name = "iamge")
    var image: ByteArray? = null

    @ColumnInfo(name = "delete")
    var delete: Boolean = false

    constructor()
    constructor(id: Int){
        this.id = id
    }
    constructor(id: Int, name: String, memo: String, image: ByteArray) {
        this.id = id
        this.name = name
        this.image = image
        this.memo = memo
    }
    constructor(id: Int, name: String, memo: String) {
        this.id = id
        this.name = name
        this.memo = memo
    }
    constructor(name: String, memo: String) {
        this.name = name
        this.memo = memo
    }
}