package uz.gita.otabek.notejon.screens.home

import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.screens.note.NoteScreen
import uz.gita.otabek.notejon.ui.navigation.AppNavigator
import javax.inject.Inject

class HomeDirections @Inject constructor(private val navigator: AppNavigator) : HomeContract.Direction {
    override suspend fun moveToAdd() {
        navigator.push(NoteScreen())
    }

    override suspend fun moveToEdit(data: NoteData) {
        navigator.push(NoteScreen(data))
    }
}