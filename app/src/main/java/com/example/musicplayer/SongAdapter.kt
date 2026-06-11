package com.example.musicplayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class SongAdapter(
    private val songs: List<Song>,
    private val onMoveUp: (Song) -> Unit,
    private val onMoveDown : (Song) -> Unit,
    private val onDelete : (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtArtist: TextView = itemView.findViewById(R.id.txtArtist)
        val txtDuration: TextView = itemView.findViewById(R.id.txtDuration)
        val btnMenu: ImageButton = itemView.findViewById(R.id.btnMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.songcard,
                parent,
                false
            )

        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {

        val song = songs[position]

        holder.txtTitle.text = song.title

        holder.txtArtist.text = song.artist
        val minutos = (song.duration) / 60
        val segundos = (song.duration) % 60
        holder.txtDuration.text = String.format("%d:%02d", minutos,segundos)

        holder.btnMenu.setOnClickListener {
            val popup = PopupMenu(holder.itemView.context, holder.btnMenu)
            popup.menu.add("subir")
            popup.menu.add("bajar")
            popup.menu.add("eliminar")

            popup.setOnMenuItemClickListener {
                when(it.title.toString()) {
                    "subir" -> onMoveUp(song)
                    "bajar" -> onMoveDown(song)
                    "eliminar" -> onDelete(song)
                }
                    true
                }
            popup.show()
            }
        }

    override fun getItemCount(): Int {
        return songs.size
    }

}