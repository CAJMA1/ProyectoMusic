package com.example.musicplayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(
    private val songs: List<Song>
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtTitle: TextView =
            itemView.findViewById(R.id.txtTitle)

        val txtArtist: TextView =
            itemView.findViewById(R.id.txtArtist)

        val txtDuration: TextView =
            itemView.findViewById(R.id.txtDuration)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.songcard,
                parent,
                false
            )

        return SongViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SongViewHolder,
        position: Int
    ) {

        val song = songs[position]

        holder.txtTitle.text = song.title

        holder.txtArtist.text = song.artist

        holder.txtDuration.text =
            "${song.duration} seg"
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}