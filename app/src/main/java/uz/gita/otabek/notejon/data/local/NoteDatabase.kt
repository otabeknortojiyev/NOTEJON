package uz.gita.otabek.notejon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.otabek.notejon.data.model.NoteData

@Database(entities = [NoteData::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
  abstract fun noteDao(): NoteDao
}