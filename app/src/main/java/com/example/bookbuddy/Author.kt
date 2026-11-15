package com.example.bookbuddy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "authors",
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["magnum"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Genre::class,
            parentColumns = ["subId"],
            childColumns = ["genre"],
            onDelete = ForeignKey.SET_DEFAULT
        )
    ]
)
data class Author(
    @PrimaryKey
    val name: String,
    @ColumnInfo(defaultValue = "-1")
    val genre: Int = -1, // "other" genre
    val magnum: Int,


)
