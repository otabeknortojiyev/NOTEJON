package uz.gita.otabek.notejon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.otabek.notejon.screens.home.HomeContract
import uz.gita.otabek.notejon.screens.home.HomeDirections
import uz.gita.otabek.notejon.screens.note.NoteContract
import uz.gita.otabek.notejon.screens.note.NoteDirections

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds ViewModelScoped]
    fun bindHomeDirection(impl: HomeDirections): HomeContract.Direction

    @[Binds ViewModelScoped]
    fun bindNoteDirection(impl: NoteDirections): NoteContract.Direction
}