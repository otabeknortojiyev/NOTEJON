package uz.gita.otabek.notejon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.otabek.notejon.data.model.TagData

@Dao
interface TagDao {
    @Query("SELECT * FROM tags")
    fun getAll(): List<TagData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTag(data: TagData)
}