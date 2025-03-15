package uz.gita.otabek.notejon.screens.note.items

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import uz.gita.otabek.notejon.screens.note.NoteContract

@Composable
fun TextColorItem(index: Int, icon: Int, textColor: Int, onClick: (NoteContract.Intent) -> Unit) {
  IconButton(onClick = {
    when (textColor) {
      index -> onClick(NoteContract.Intent.ChangeTextColor(0))
      else -> onClick(NoteContract.Intent.ChangeTextColor(index))
    }
  }) {
    Image(painter = painterResource(id = icon), contentDescription = null)
  }
}