package uz.gita.otabek.notejon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.data.model.TagData

@Database(entities = [NoteData::class, TagData::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
}