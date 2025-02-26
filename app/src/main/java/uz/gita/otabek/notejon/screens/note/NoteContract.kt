package uz.gita.otabek.notejon.screens.note

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.gita.otabek.notejon.data.model.NoteData

interface NoteContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEvent(intent: Intent): Job
    }

    data class UiState(
        val isLoading: Boolean = false,
        val backgroundColor: Int = 0,
        val textColor: Int = 0,
        val textAlign: Int = 0,
        var favorite: Boolean = false,
        val edit: Boolean = false,
        val error: Boolean = false,
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun back()
    }


    interface Intent {
        data class Error(val error: Boolean) : Intent
        data object Back : Intent
        data class ChangeBack(val int: Int) : Intent
        data class ChangeTextColor(val int: Int) : Intent
        data class ChangeTextAlign(val int: Int) : Intent
        data class SaveNote(val data: NoteData) : Intent
        data class ChangeFavorite(val favorite: Boolean) : Intent
        data class SetProperties(val data: NoteData) : Intent
    }
}