package com.example.musicplayer

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var dbHelper: dbMusicLayer
    private lateinit var txtMinutos : TextView
    private lateinit var btnAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recycler = findViewById(R.id.recyclerSongs)

        dbHelper = dbMusicLayer(this)

        recycler.layoutManager = LinearLayoutManager(this)
        txtMinutos = findViewById(R.id.txtDuracionT)

        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }


        cargarCanciones()
    }
    override fun onResume() {
        super.onResume()

        cargarCanciones()
    }
    private fun cargarCanciones() {
        val songs = dbHelper.getSongs()

        recycler.adapter = SongAdapter(songs,
            onMoveUp = {song ->
                if(song.position == 0) return@SongAdapter
                val db = dbHelper.writableDatabase
                db.execSQL("UPDATE musiclayer " +
                        "SET position = position + 1 " +
                        "WHERE position = ?", arrayOf(song.position -1))

                db.execSQL("UPDATE musiclayer " +
                            "SET position = ? " +
                            "WHERE id = ?", arrayOf(song.position - 1, song.id)
                )
                db.close()
                cargarCanciones()
            },
            onMoveDown = {song ->
                val cancionesLista = dbHelper.getSongs()
                val index = cancionesLista.indexOfFirst { it.id == song.id }
                if(index >= cancionesLista.size -1) return@SongAdapter
                val db = dbHelper.writableDatabase
                db.execSQL("UPDATE musiclayer " +
                        "SET position = position - 1 " +
                        "WHERE position = ?", arrayOf(song.position + 1))

                db.execSQL("UPDATE musiclayer " +
                        "SET position = ? " +
                        "WHERE id = ?", arrayOf(song.position + 1, song.id)
                )
                db.close()
                cargarCanciones()
            },
            onDelete = {song ->
                val db = dbHelper.readableDatabase
                db.delete("musiclayer","id = ?", arrayOf(song.id.toString()))
                db.close()
                cargarCanciones()
            }
            )
        actualizarDuracion()
    }
    private fun actualizarDuracion(){
        val db = dbHelper.readableDatabase
        var totalSegundos = 0
        val cursor = db.rawQuery("SELECT SUM(duration) FROM musiclayer",null)

        if(cursor.moveToFirst()){
            totalSegundos = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        val minutos = totalSegundos / 60
        val segundos = totalSegundos % 60
        txtMinutos.text = "Duracion total:" + " " + String.format("%d:%02d",minutos,segundos)
    }
}