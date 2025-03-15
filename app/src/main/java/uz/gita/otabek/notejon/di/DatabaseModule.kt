package uz.gita.otabek.notejon.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.otabek.notejon.data.local.NoteDao
import uz.gita.otabek.notejon.data.local.NoteDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase {
    return Room.databaseBuilder(
      context = context, klass = NoteDatabase::class.java, name = "Database.db"
    ).build()
  }

  @Provides
  @Singleton
  fun provideNoteDao(database: NoteDatabase): NoteDao {
    return database.noteDao()
  }
}