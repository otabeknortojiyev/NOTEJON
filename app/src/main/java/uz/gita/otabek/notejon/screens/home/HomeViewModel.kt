package uz.gita.otabek.notejon.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val direction: HomeContract.Direction, private val repository: NoteRepository,
) : ViewModel(), HomeContract.ViewModel {
  override fun onEvent(intent: HomeContract.Intent) = intent {
    when (intent) {
      HomeContract.Intent.Init -> {
        reduce { state.copy(isLoading = true, isFavorite = false, isAll = true) }
        val notes = repository.getAllNotes()
        reduce { state.copy(notes = notes, isLoading = false) }
      }

      is HomeContract.Intent.MoveToAdd -> {
        direction.moveToAdd(intent.data)
      }

      HomeContract.Intent.LoadFavNotes -> {
        reduce { state.copy(isLoading = true, isAll = false, isFavorite = true) }
        val notes = repository.getFavNotes()
        reduce { state.copy(isLoading = false, notes = notes, isFavorite = true) }
      }

      is HomeContract.Intent.UpdateFavNote -> {
        reduce { state.copy(isLoading = true) }
        repository.updateNote(intent.data)
        val notes = mutableListOf<NoteData>()
        if (state.isFavorite) {
          notes.addAll(repository.getFavNotes())
        } else {
          notes.addAll(repository.getAllNotes())
        }
        reduce { state.copy(isLoading = false, notes = notes) }
      }

      is HomeContract.Intent.DeleteNote -> {
        reduce { state.copy(isLoading = true) }
        repository.deleteNote(intent.data)
        val notes = mutableListOf<NoteData>()
        if (state.isFavorite) {
          notes.addAll(repository.getFavNotes())
        } else {
          notes.addAll(repository.getAllNotes())
        }
        reduce { state.copy(notes = notes, isLoading = false) }
      }
    }
  }

  override val container = container<HomeContract.UiState, HomeContract.SideEffect>(HomeContract.UiState())
}