package uz.gita.otabek.notejon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.otabek.notejon.repository.NoteRepository
import uz.gita.otabek.notejon.repository.NoteRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindRepository(impl: NoteRepositoryImpl): NoteRepository
}