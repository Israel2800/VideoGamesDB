package com.israelaguilar.videogamesdb.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.israelaguilar.videogamesdb.application.VideoGamesDBApp
import com.israelaguilar.videogamesdb.data.GameRepository
import com.israelaguilar.videogamesdb.data.db.model.GameEntity
import com.israelaguilar.videogamesdb.databinding.ActivityMainBinding
import com.israelaguilar.videogamesdb.util.Constants
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var games: MutableList<GameEntity> = mutableListOf()
    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        repository = (application as VideoGamesDBApp).repository

        updateUI()

        /*val game = GameEntity(
            title = "Super Mario Galaxy",
            genre = "Plataformas",
            developer = "Nintendo"
        )

        // Insert data on table
        lifecycleScope.launch {
            repository.insertGame(game)
        }*/
    }

    fun click(view: View) {
        // Manejamos el click del floating action button
    }

    private fun updateUI(){
        lifecycleScope.launch {
            games = repository.getAllGames()

            binding.tvSinRegistros.visibility =
                if(games.isNotEmpty()) View.INVISIBLE else View.VISIBLE
        }
    }
}