package com.israelaguilar.videogamesdb.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.israelaguilar.videogamesdb.util.Constants

@Entity(tableName = Constants.DATABASE_GAME_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    var id: Long = 0,
    @ColumnInfo(name = "game_title")
    var title: String,
    @ColumnInfo(name = "game_genre")
    var genre: String,
    @ColumnInfo(name = "game_developer", defaultValue = "Desconocido")
    var developer: String
    /*@ColumnInfo(name = "game_rating", defaultValue = "-1")
    var rating: Int*/
)
