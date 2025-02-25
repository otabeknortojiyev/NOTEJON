package uz.gita.otabek.notejon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tags")
data class TagData(
    @PrimaryKey
    val name: String,
)
