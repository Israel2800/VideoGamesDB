package com.israelaguilar.videogamesdb.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.israelaguilar.videogamesdb.R
import com.israelaguilar.videogamesdb.application.VideoGamesDBApp
import com.israelaguilar.videogamesdb.data.GameRepository
import com.israelaguilar.videogamesdb.data.db.model.GameEntity
import com.israelaguilar.videogamesdb.databinding.GameDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class GameDialog(
    private val newGame: Boolean = true,
    private var game: GameEntity = GameEntity(
        title = "",
        genre = "",
        developer = ""
    ),
    private val updateUI: () -> Unit
): DialogFragment() {

    private var _binding: GameDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog

    private var saveButton: Button? = null

    private lateinit var  repository: GameRepository


    // Aquí se crea y configura de forma inicial el dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = GameDialogBinding.inflate(requireActivity().layoutInflater)

        repository = (requireContext().applicationContext as VideoGamesDBApp).repository

        builder = AlertDialog.Builder(requireContext())

        // Establecemos en los text input edit text los valores del objeto game

        binding.apply {
            tietTitle.setText(game.title)
            tietGenre.setText(game.genre)
            tietDeveloper.setText(game.developer)
        }

        dialog = if(newGame)
            buildDialog("Guardar", "Cancelar", {
                // Acción de guardar
                // Aquí obetenemos los textos ingresados y se los asignamos a nuestro objeto game

                binding.apply {
                    game.apply {
                        title = tietTitle.text.toString()
                        genre = tietGenre.text.toString()
                        developer = tietDeveloper.text.toString()
                    }
                }

                try {

                    lifecycleScope.launch(Dispatchers.IO) {
                        repository.insertGame(game)
                    }

                    Toast.makeText(
                        requireContext(),
                        "Juego guardado exitosamente",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    updateUI()

                }catch (e: IOException){
                    Toast.makeText(
                        requireContext(),
                        "Error al guardar el juego",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }, {
                // Acción de cancelar

            })
        else
            buildDialog("Actualizar", "Borrar", {
                // Acción de actualizar
                // Aquí obetenemos los textos ingresados y se los asignamos a nuestro objeto game
                binding.apply {
                    game.apply {
                        title = tietTitle.text.toString()
                        genre = tietGenre.text.toString()
                        developer = tietDeveloper.text.toString()
                    }
                }

                try {

                    lifecycleScope.launch(Dispatchers.IO) {
                        repository.updateGame(game)
                    }

                    Toast.makeText(
                        requireContext(),
                        "Juego actualizado exitosamente",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    updateUI()

                }catch (e: IOException){
                    Toast.makeText(
                        requireContext(),
                        "Error al actualizar el juego",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }, {
                // Acción de borrar
                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        repository.deleteGame(game)
                    }
                    Toast.makeText(
                        requireContext(),
                        "Juego borrado exitosamente",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    updateUI()

                }catch (e: IOException){
                    Toast.makeText(
                        requireContext(),
                        "Error al borrar el juego",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })


        /*
        dialog = builder.setView(binding.root)
            .setTitle(getString(R.string.game))
            .setPositiveButton("Guardar", DialogInterface.OnClickListener { _, _ ->
                // Click para el botón positivo

                // Aquí obetenemos los textos ingresados y se los asignamos a nuestro objeto game
                binding.apply {
                    game.apply {
                        title = tietTitle.text.toString()
                        genre = tietGenre.text.toString()
                        developer = tietDeveloper.text.toString()
                    }
                }

                try {

                    lifecycleScope.launch(Dispatchers.IO) {
                        repository.insertGame(game)
                    }

                    Toast.makeText(
                        requireContext(),
                        "Juego guardado exitosamente",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    updateUI()

                }catch (e: IOException){
                    Toast.makeText(
                        requireContext(),
                        "Error al guardar el juego",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            })
            .setNegativeButton("Cancelar"){ _, _ ->
                // CLick para el botón negativo
            }
            .create()
        */
        return dialog

    }

    // Aquí es cuando se destruye
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // Se llama después de que se muestra el dialog en pantalla
    override fun onStart() {
        super.onStart()

        // Debido a que la clase dialog no me permite referenciarme a sus botones
        val alertDialog = dialog as AlertDialog

        saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

        saveButton?.isEnabled = false


        // Forma larga de hacerlo
       /* binding.tietTitle.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })

        binding.tietGenre.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()

            }

        })

        binding.tietDeveloper.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()

            }

        }) */

        binding.apply {
            setupTextWatcher(
                tietTitle,
                tietGenre,
                tietDeveloper
            )
        }

    }



    private fun validateFields(): Boolean
        = binding.tietTitle.text.toString().isNotEmpty() &&
            binding.tietGenre.text.toString().isNotEmpty() &&
            binding.tietDeveloper.text.toString().isNotEmpty()

    // Forma corta de hacerlo
    private fun setupTextWatcher(vararg textFields: TextInputEditText){
        val textWatcher = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        }

        textFields.forEach { textField ->
            textField.addTextChangedListener(textWatcher)
        }
    }

    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButton: () -> Unit,
        negativeButton: () -> Unit
    ): Dialog =
        builder.setView(binding.root)
            .setTitle(R.string.game)
            .setPositiveButton(btn1Text){_, _ ->
                // Acción para el botón positivo
                positiveButton()
            } .setNegativeButton(btn2Text){ _, _ ->
                // Acctión para el botón negativo
                negativeButton()
            }
            .create()

}