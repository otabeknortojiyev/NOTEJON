package uz.gita.otabek.notejon.repository

import uz.gita.otabek.notejon.data.local.NoteDao
import uz.gita.otabek.notejon.data.local.TagDao
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.data.model.TagData
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao, private val tagDao: TagDao) : NoteRepository {
    override fun getAllNotes(): List<NoteData> {
        return noteDao.getAll()
    }

    override fun insertNote(data: NoteData) {
        noteDao.insert(data)
    }

    override fun getAllTags(): List<TagData> {
        return tagDao.getAll()
    }

    override fun insertTag(data: TagData) {
        tagDao.insertTag(data)
    }

    override fun getNotesByTag(tag: String): List<NoteData> {
        return noteDao.getByTag(tag)
    }

    override fun getFavNotes(): List<NoteData> {
        return noteDao.getByFavorite()
    }

    override fun updateNote(data: NoteData) {
        noteDao.update(data)
    }

    override fun deleteNote(data: NoteData) {
        noteDao.delete(data)
    }
}