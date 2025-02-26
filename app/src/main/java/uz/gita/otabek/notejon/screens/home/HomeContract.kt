package uz.gita.otabek.notejon.screens.home

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.data.model.TagData

interface HomeContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEvent(intent: Intent): Job
    }

    data class UiState(
        val isLoading: Boolean = false,
        val notes: List<NoteData> = emptyList(),
        val tags: List<TagData> = emptyList(),
        val isAll: Boolean = true,
        val isByTags: Boolean = false,
        val isFavorite: Boolean = false,
        val data: NoteData? = null,
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToAdd()
        suspend fun moveToEdit(data: NoteData)
    }


    interface Intent {
        data object Init : Intent
        data object MoveToAdd : Intent
        data object LoadTags : Intent
        data class LoadNotesByTag(val tag: String) : Intent
        data object LoadFavNotes : Intent
        data class UpdateFavNote(val data: NoteData, val index: Int) : Intent
        data class Edit(val data: NoteData) : Intent
        data class DeleteNote(val data: NoteData) : Intent
    }
}