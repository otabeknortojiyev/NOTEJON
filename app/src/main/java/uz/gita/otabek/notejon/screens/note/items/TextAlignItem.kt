package uz.gita.otabek.notejon.screens.note.items

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import uz.gita.otabek.notejon.screens.note.NoteContract

@Composable
fun TextAlignItem(index: Int, icon: Int, textAlign: Int, onClick: (NoteContract.Intent) -> Unit) {
  IconButton(onClick = {
    when (textAlign) {
      index -> onClick(NoteContract.Intent.ChangeTextAlign(0))
      else -> onClick(NoteContract.Intent.ChangeTextAlign(index))
    }
  }) {
    Image(painter = painterResource(id = icon), contentDescription = null)
  }
}