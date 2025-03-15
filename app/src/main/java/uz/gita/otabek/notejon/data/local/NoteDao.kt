package uz.gita.otabek.notejon.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uz.gita.otabek.notejon.data.model.NoteData

@Dao
interface NoteDao {
  @Query("SELECT * FROM notes")
  fun getAll(): List<NoteData>

  @Query("SELECT * FROM notes WHERE favorite IS 1")
  fun getByFavorite(): List<NoteData>

  @Insert
  fun insert(data: NoteData)

  @Update
  fun update(data: NoteData)

  @Delete
  fun delete(data: NoteData)
}