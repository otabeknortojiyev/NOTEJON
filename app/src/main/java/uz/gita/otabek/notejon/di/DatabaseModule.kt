package uz.gita.otabek.notejon.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.otabek.notejon.data.local.NoteDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun provideBookDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context = context, NoteDatabase::class.java, "Database.db")
            .allowMainThreadQueries()
            .build()
}