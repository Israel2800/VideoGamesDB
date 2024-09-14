package com.israelaguilar.videogamesdb.application

import android.app.Application
import com.israelaguilar.videogamesdb.data.GameRepository
import com.israelaguilar.videogamesdb.data.db.GameDatabase

// Esta clase me representa a la app
// Será útil si queremos después agregar inyección de dependencias con Dagger Hilt
class VideoGamesDBApp: Application() {

    private val database by lazy{
        GameDatabase.getDataBase(this@VideoGamesDBApp)
    }

    val repository by lazy {
        GameRepository(database.gameDao())
    }

    
}