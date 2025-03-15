package uz.gita.otabek.notejon.screens.home

import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.screens.note.NoteScreen
import uz.gita.otabek.notejon.ui.navigation.AppNavigator
import javax.inject.Inject

class HomeDirections @Inject constructor(private val navigator: AppNavigator) : HomeContract.Direction {
  override suspend fun moveToAdd(data: NoteData?) {
    navigator.push(NoteScreen(data = data))
  }
}