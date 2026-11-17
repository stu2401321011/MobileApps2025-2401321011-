package com.example.bookbuddy.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = Author::class,
            parentColumns = ["name"],
            childColumns = ["author"],
            onDelete = ForeignKey.SET_DEFAULT
        ),
        ForeignKey(
            entity = Genre::class,
            parentColumns = ["subId"],
            childColumns = ["genre"],
            onDelete = ForeignKey.SET_DEFAULT
        )
    ],
    indices = [Index("author"),
    Index("genre"), Index("date")]
)

data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    @ColumnInfo(defaultValue = "Unknown")
    val author: String? = "Unknown",
    @ColumnInfo(defaultValue = "-1")
    val genre: Int = -1,
    val rating: Double? = null,
    val review: String?,
    val status: String = "downloaded",
    val date:  Long = 0L, // Epoch days (or YYYYMMDD) representing day/month/year without time
    val favourite: Boolean = false,
    val filepath: String,
    val imgpath: String
)
