package com.israelaguilar.videogamesdb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.israelaguilar.videogamesdb.data.db.model.GameEntity
import com.israelaguilar.videogamesdb.util.Constants

@Database(
    entities = [GameEntity::class],
    version = 1, // Versión de la bd para migraciones
    exportSchema = true // por defecto es true
)
abstract class GameDatabase: RoomDatabase() {
    // Aquí va el DAO
    abstract fun gameDao(): GameDao

    // Sin inyección de dependencias, instanciamos la base de datos
    // Aquí con un patrón singleton

    companion object{

        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDataBase(context: Context): GameDatabase{
            // Si la instancia no es nula, entonces vamos a regresar la que ya tenemos
            // Si es nula, creamos una instancia y la regresamos
            // (Patrón singleton)

            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }

    }

}