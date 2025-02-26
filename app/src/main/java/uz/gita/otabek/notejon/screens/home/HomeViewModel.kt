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
                reduce { state.copy(isLoading = true, isByTags = false, isFavorite = false, isAll = true) }
                val notes = repository.getAllNotes()
                reduce { state.copy(notes = notes, isLoading = false, isFavorite = false) }
            }

            HomeContract.Intent.MoveToAdd -> {
                direction.moveToAdd()
            }

            HomeContract.Intent.LoadTags -> {
                reduce { state.copy(isLoading = true, isAll = false, isFavorite = false, isByTags = true) }
                val tags = repository.getAllTags()
                reduce { state.copy(tags = tags, isLoading = false) }
            }

            is HomeContract.Intent.LoadNotesByTag -> {
                reduce { state.copy(isLoading = true) }
                val notes = repository.getNotesByTag(intent.tag)
                reduce { state.copy(isLoading = false, notes = notes, isFavorite = false) }
            }

            HomeContract.Intent.LoadFavNotes -> {
                reduce { state.copy(isLoading = true, isAll = false, isByTags = false, isFavorite = true) }
                val notes = repository.getFavNotes()
                reduce { state.copy(isLoading = false, notes = notes, isFavorite = true) }
            }

            is HomeContract.Intent.UpdateFavNote -> {
                reduce { state.copy(isLoading = true) }
                repository.updateNote(intent.data)
                val notes = mutableListOf<NoteData>()
                if (state.isFavorite) {
                    for (index in 0 until state.notes.size) {
                        if (index != intent.index) {
                            notes.add(state.notes[index])
                        }
                    }
                } else {
                    for (index in 0 until state.notes.size) {
                        if (index != intent.index) {
                            notes.add(state.notes[index])
                        } else {
                            notes.add(
                                NoteData(
                                    state.notes[index].id,
                                    state.notes[index].title,
                                    state.notes[index].text,
                                    state.notes[index].date,
                                    intent.data.favorite,
                                    state.notes[index].tag,
                                    state.notes[index].textColor,
                                    state.notes[index].backgroundColor,
                                    state.notes[index].textAlign
                                )
                            )
                        }
                    }
                }
                reduce { state.copy(notes = notes, isLoading = false) }
            }

            is HomeContract.Intent.Edit -> {
                direction.moveToEdit(intent.data)
            }

            is HomeContract.Intent.DeleteNote -> {
                reduce { state.copy(isLoading = true) }
                repository.deleteNote(intent.data)
                val notes = mutableListOf<NoteData>()
                for (data in state.notes) {
                    if (intent.data.id != data.id) {
                        notes.add(data)
                    }
                }
                reduce { state.copy(notes = notes, isLoading = false) }
            }
        }
    }

    override val container = container<HomeContract.UiState, HomeContract.SideEffect>(HomeContract.UiState())
}