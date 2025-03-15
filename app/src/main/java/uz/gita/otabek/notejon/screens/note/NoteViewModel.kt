package uz.gita.otabek.notejon.screens.note

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
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
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
  private val direction: NoteContract.Direction,
  private val repository: NoteRepository,
) : ViewModel(), NoteContract.ViewModel {
  override fun onEvent(intent: NoteContract.Intent) = intent {
    when (intent) {
      NoteContract.Intent.Back -> direction.back()
      is NoteContract.Intent.ChangeBack -> reduce { state.copy(backgroundColor = intent.int) }
      is NoteContract.Intent.ChangeTextColor -> reduce { state.copy(textColor = intent.int) }
      is NoteContract.Intent.ChangeTextAlign -> reduce { state.copy(textAlign = intent.int) }
      is NoteContract.Intent.SaveNote -> {
        when (state.edit) {
          true -> repository.updateNote(intent.data)
          else -> repository.insertNote(intent.data)
        }
        direction.back()
      }

      is NoteContract.Intent.ChangeFavorite -> {
        reduce { state.copy(favorite = intent.favorite) }
      }

      is NoteContract.Intent.SetProperties -> {
        reduce {
          state.copy(
            edit = true,
            favorite = intent.data.favorite,
            backgroundColor = getBackgroundColor(intent.data.backgroundColor),
            textColor = getTextColor(intent.data.textColor),
            textAlign = getTextAlign(intent.data.textAlign)
          )
        }
      }

      is NoteContract.Intent.Error -> {
        reduce { state.copy(error = intent.error) }
      }
    }
  }

  private fun getBackgroundColor(value: Long) = mapOf(
    BackBlue.value.toLong() to 1,
    BackRed.value.toLong() to 2,
    BackGreen.value.toLong() to 3,
    BackYellow.value.toLong() to 4
  ).getOrDefault(value, 0)

  private fun getTextColor(value: Long) = mapOf(
    TextGray.value.toLong() to 1,
    TextYellow.value.toLong() to 2,
    TextBlue.value.toLong() to 3,
    TextRed.value.toLong() to 4,
    TextGreen.value.toLong() to 5
  ).getOrDefault(value, 0)

  private fun getTextAlign(value: Int) = mapOf(0 to 0, 1 to 1, 2 to 2).getOrDefault(value, 3)

  override val container = container<NoteContract.UiState, NoteContract.SideEffect>(NoteContract.UiState())
}