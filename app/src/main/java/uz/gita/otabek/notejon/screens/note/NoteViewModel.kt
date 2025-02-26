package uz.gita.otabek.notejon.screens.note

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.otabek.notejon.data.model.TagData
import uz.gita.otabek.notejon.repository.NoteRepository
import uz.gita.otabek.notejon.ui.theme.BackBlue
import uz.gita.otabek.notejon.ui.theme.BackGreen
import uz.gita.otabek.notejon.ui.theme.BackRed
import uz.gita.otabek.notejon.ui.theme.BackYellow
import uz.gita.otabek.notejon.ui.theme.TextBlue
import uz.gita.otabek.notejon.ui.theme.TextGray
import uz.gita.otabek.notejon.ui.theme.TextGreen
import uz.gita.otabek.notejon.ui.theme.TextRed
import uz.gita.otabek.notejon.ui.theme.TextYellow
import uz.gita.otabek.notejon.utils.AlignEnum
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val direction: NoteContract.Direction,
    private val repository: NoteRepository,
) : ViewModel(), NoteContract.ViewModel {

    override fun onEvent(intent: NoteContract.Intent) = intent {
        when (intent) {
            NoteContract.Intent.Back -> {
                direction.back()
            }

            is NoteContract.Intent.ChangeBack -> {
                reduce { state.copy(backgroundColor = intent.int) }
            }

            is NoteContract.Intent.ChangeTextColor -> {
                reduce { state.copy(textColor = intent.int) }
            }

            is NoteContract.Intent.ChangeTextAlign -> {
                reduce { state.copy(textAlign = intent.int) }
            }

            is NoteContract.Intent.SaveNote -> {
                if (state.edit) {
                    repository.updateNote(intent.data)
                } else {
                    repository.insertNote(intent.data)
                }
                if (intent.data.tag.isNotEmpty()) repository.insertTag(TagData(intent.data.tag))
                direction.back()
            }

            is NoteContract.Intent.ChangeFavorite -> {
                reduce { state.copy(favorite = intent.favorite) }
            }

            is NoteContract.Intent.SetProperties -> {
                reduce { state.copy(edit = true) }
                if (intent.data.favorite) {
                    reduce { state.copy(favorite = true) }
                } else {
                    reduce { state.copy(favorite = false) }
                }
                when (intent.data.backgroundColor) {
                    BackBlue.value.toLong() -> {
                        reduce { state.copy(backgroundColor = 1) }
                    }

                    BackRed.value.toLong() -> {
                        reduce { state.copy(backgroundColor = 2) }
                    }

                    BackGreen.value.toLong() -> {
                        reduce { state.copy(backgroundColor = 3) }
                    }

                    BackYellow.value.toLong() -> {
                        reduce { state.copy(backgroundColor = 4) }
                    }

                    else -> {
                        reduce { state.copy(backgroundColor = 0) }
                    }
                }
                when (intent.data.textColor) {
                    TextGray.value.toLong() -> {
                        reduce { state.copy(textColor = 1) }
                    }

                    TextYellow.value.toLong() -> {
                        reduce { state.copy(textColor = 2) }
                    }

                    TextBlue.value.toLong() -> {
                        reduce { state.copy(textColor = 3) }
                    }

                    TextRed.value.toLong() -> {
                        reduce { state.copy(textColor = 4) }
                    }

                    TextGreen.value.toLong() -> {
                        reduce { state.copy(textColor = 5) }
                    }

                    else -> {
                        reduce { state.copy(textColor = 0) }
                    }
                }
                when (intent.data.textAlign) {
                    AlignEnum.JUSTIFY.value -> {
                        reduce { state.copy(textAlign = 0) }
                    }

                    AlignEnum.RIGHT.value -> {
                        reduce { state.copy(textAlign = 1) }
                    }

                    AlignEnum.CENTER.value -> {
                        reduce { state.copy(textAlign = 2) }
                    }

                    else -> {
                        reduce { state.copy(textAlign = 3) }
                    }
                }
            }

            is NoteContract.Intent.Error -> {
                reduce { state.copy(error = intent.error) }
            }
        }
    }

    override val container = container<NoteContract.UiState, NoteContract.SideEffect>(NoteContract.UiState())
}