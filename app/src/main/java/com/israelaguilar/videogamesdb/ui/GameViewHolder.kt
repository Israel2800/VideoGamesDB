package com.israelaguilar.videogamesdb.ui

import androidx.recyclerview.widget.RecyclerView
import com.israelaguilar.videogamesdb.data.db.model.GameEntity
import com.israelaguilar.videogamesdb.databinding.GameElementBinding

class GameViewHolder(
    private val binding: GameElementBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(game: GameEntity){

        binding.apply {
            tvTitle.text = game.title
            tvGenre.text = game.genre
            tvDeveloper.text = game.developer
        }

    }


}