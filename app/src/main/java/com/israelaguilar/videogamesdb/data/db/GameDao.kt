package com.israelaguilar.videogamesdb.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.israelaguilar.videogamesdb.data.db.model.GameEntity
import com.israelaguilar.videogamesdb.util.Constants

@Dao
interface GameDao {

    // Funciones para interactuar con la base de datos

    // CRUD
    // Create
    @Insert
    suspend fun insertGame(game: GameEntity)

    @Insert
    suspend fun insertGames(game: List<GameEntity>)

    // Read
    @Query("SELECT * FROM ${Constants.DATABASE_GAME_TABLE}")
    suspend fun getAllGames(): MutableList<GameEntity>
    // Update
    @Update
    suspend fun updateGame(game: GameEntity)

    // Delete
    @Delete
    suspend fun deleteGame(game: GameEntity)
}