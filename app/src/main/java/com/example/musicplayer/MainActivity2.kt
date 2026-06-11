package com.example.musicplayer

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    lateinit var editTitulo: EditText
    lateinit var editArtista: EditText
    lateinit var editDuracion: EditText
    lateinit var editTono: EditText
    lateinit var btnGuardar: Button
    lateinit var dbHelper: dbMusicLayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTitulo = findViewById(R.id.editTitulo)
        editArtista = findViewById(R.id.editArtista)
        editDuracion = findViewById(R.id.editDuracion)
        editTono = findViewById(R.id.editTono)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            val titulo = editTitulo.text.toString()
            val artista = editArtista.text.toString()
            val duracion = editDuracion.text.toString().toIntOrNull() ?: 0
            val tono = editTono.text.toString()
            val position = dbHelper.getNextPosition()

            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("title", titulo)
                put("artist", artista)
                put("duration", duracion)
                put("tone", tono)
                put("position", position)
            }
            db.insert("musiclayer", null, values)
            db.close()
            finish()
        }
        dbHelper = dbMusicLayer(this)
        }

}
