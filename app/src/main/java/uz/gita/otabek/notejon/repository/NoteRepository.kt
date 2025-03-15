package uz.gita.otabek.notejon.repository

import uz.gita.otabek.notejon.data.model.NoteData

interface NoteRepository {
  fun getAllNotes(): List<NoteData>
  fun insertNote(data: NoteData)
  fun getFavNotes(): List<NoteData>
  fun updateNote(data: NoteData)
  fun deleteNote(data: NoteData)
}