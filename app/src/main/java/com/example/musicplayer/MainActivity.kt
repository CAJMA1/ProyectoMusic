package com.example.musicplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var dbHelper: dbMusicLayer
    lateinit var btnAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        recycler =
            findViewById(
                R.id.recyclerSongs
            )


        dbHelper = dbMusicLayer(this)

        recycler.layoutManager =
            LinearLayoutManager(this)

        cargarCanciones()
    }
    override fun onResume() {
        super.onResume()

        cargarCanciones()
    }
    private fun cargarCanciones() {

        val songs = dbHelper.getSongs()

        recycler.adapter = SongAdapter(songs)
    }
}