package com.example.bookbuddy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
    foreignKeys = [
        ForeignKey(
            entity = Genre::class,
            parentColumns = ["subId"],
            childColumns = ["mainId"],
            onDelete = ForeignKey.SET_DEFAULT
        )
    ]
)

data class Genre(
    @PrimaryKey
    val subId : Int,
    @ColumnInfo(defaultValue = "0")
    val mainId: Int = 0,  // 0 means this is main genre category
    val name: String
)
