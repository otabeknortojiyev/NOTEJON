package uz.gita.otabek.notejon.repository

import android.provider.ContactsContract.CommonDataKinds.Note
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.data.model.TagData

interface NoteRepository {
    fun getAllNotes(): List<NoteData>
    fun insertNote(data: NoteData)
    fun getAllTags(): List<TagData>
    fun insertTag(data: TagData)
    fun getNotesByTag(tag: String): List<NoteData>
    fun getFavNotes(): List<NoteData>
    fun updateNote(data: NoteData)
    fun deleteNote(data: NoteData)
}