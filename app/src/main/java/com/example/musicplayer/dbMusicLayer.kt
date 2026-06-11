package com.example.musicplayer
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbMusicLayer(context: Context) :
    SQLiteOpenHelper(context, "musiclayer.db", null, 1){
        override fun onCreate(db: SQLiteDatabase){
            db.execSQL(
                "CREATE TABLE musiclayer(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "title TEXT NOT NULL, " +
                        "duration INTEGER NOT NULL, " +
                        "artist TEXT NOT NULL, " +
                        "tone TEXT NOT NULL, " +
                        "position INTEGER NOT NULL" +
                        ")"
            )
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS musiclayer")
            onCreate(db)
        }

    fun getSongs(): MutableList<Song> {
        val songs = mutableListOf<Song>()

        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM musiclayer ORDER BY position ASC",null)
        while (cursor.moveToNext()) {
            songs.add(
                Song(
                    cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                    ),
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("title")
                    ),
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("artist")
                    ),
                    cursor.getInt(
                        cursor.getColumnIndexOrThrow("duration")
                    ),
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("tone")
                    ),
                    cursor.getInt(
                        cursor.getColumnIndexOrThrow("position")
                    )
                )
            )
        }
        cursor.close()
        return songs
    }
    fun getNextPosition(): Int {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT MAX(position) FROM musiclayer",
            null
        )

        var nextPosition = 1

        if (cursor.moveToFirst()) {
            nextPosition = cursor.getInt(0) + 1
        }

        cursor.close()

        return nextPosition
    }
}
