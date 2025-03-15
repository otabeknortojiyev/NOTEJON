package uz.gita.otabek.notejon.screens.home

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.gita.otabek.notejon.data.model.NoteData

interface HomeContract {

  interface ViewModel : ContainerHost<UiState, SideEffect> {
    fun onEvent(intent: Intent): Job
  }

  data class UiState(
    val isLoading: Boolean = false,
    val notes: List<NoteData> = emptyList(),
    val isAll: Boolean = true,
    val isFavorite: Boolean = false,
    val data: NoteData? = null,
  )

  sealed interface SideEffect

  interface Direction {
    suspend fun moveToAdd(data: NoteData?)
  }


  interface Intent {
    data object Init : Intent
    data class MoveToAdd(val data: NoteData? = null) : Intent
    data object LoadFavNotes : Intent
    data class UpdateFavNote(val data: NoteData) : Intent
    data class DeleteNote(val data: NoteData) : Intent
  }
}