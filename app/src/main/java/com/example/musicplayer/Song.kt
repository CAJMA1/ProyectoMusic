package com.example.musicplayer

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val duration: Int,
    val tone: String,
    val position: Int
)