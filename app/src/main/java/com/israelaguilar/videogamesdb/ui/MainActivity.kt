package com.israelaguilar.videogamesdb.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.israelaguilar.videogamesdb.R
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

    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        repository = (application as VideoGamesDBApp).repository

        gameAdapter = GameAdapter{ selectedGame ->

            // Click al registro de cada juego

            val dialog = GameDialog(newGame = false, game = selectedGame, updateUI = {
                updateUI()
            }, message = { text ->
                // Aquí va la función para los mensajes
                message(text)
            })

            dialog.show(supportFragmentManager, "dialog2")


            /*Toast.makeText(
                this,
                "Click en el juego: ${game.title}, con género: ${game.genre}",
                Toast.LENGTH_SHORT
            )
                .show()*/
        }

        // Establezco el recyclerview
        binding.rvGames.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameAdapter
        }




        /*val game = GameEntity(
            title = "FIFA 23",
            genre = "Deportes",
            developer = "EA Sports"
        )

        // Insert data on table
        lifecycleScope.launch {
            repository.insertGame(game)
        }*/

        updateUI()

    }

    fun click(view: View) {
        // Manejamos el click del floating action button
        val dialog = GameDialog(updateUI = {
            updateUI()
        }, message = { text ->
            // Aquí va el mensaje
            message(text)

        })

        dialog.show(supportFragmentManager, "dialog1")



    }

    private fun message(text: String){
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_SHORT
        ).show()

        Snackbar.make(
            binding.cl,
            text,
            Snackbar.LENGTH_SHORT
        )
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.snackbar)) // #9E1734
            .show()
    }

    private fun updateUI(){
        lifecycleScope.launch {
            games = repository.getAllGames()

            binding.tvSinRegistros.visibility =
                if(games.isNotEmpty()) View.INVISIBLE else View.VISIBLE

            gameAdapter.updateList(games)
        }
    }
}