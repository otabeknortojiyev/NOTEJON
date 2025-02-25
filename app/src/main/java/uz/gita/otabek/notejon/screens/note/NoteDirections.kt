package uz.gita.otabek.notejon.screens.note

import uz.gita.otabek.notejon.ui.navigation.AppNavigator
import javax.inject.Inject

class NoteDirections @Inject constructor(private val navigator: AppNavigator) : NoteContract.Direction {
    override suspend fun back() {
        navigator.back()
    }
}