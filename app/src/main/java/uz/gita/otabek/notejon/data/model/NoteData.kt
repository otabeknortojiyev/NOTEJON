package uz.gita.otabek.notejon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class NoteData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val text: String,
    val date: Long,
    val favorite: Boolean,
    val tag: String,
    val textColor: Long,
    val backgroundColor: Long,
    val textAlign: String
)
