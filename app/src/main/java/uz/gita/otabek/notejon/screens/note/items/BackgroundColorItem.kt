package uz.gita.otabek.notejon.screens.note.items

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import uz.gita.otabek.notejon.screens.note.NoteContract

@Composable
fun BackgroundColorItem(index: Int, icon: Int, backgroundColor: Int, onClick: (NoteContract.Intent) -> Unit) {
  IconButton(onClick = {
    when (backgroundColor) {
      index -> onClick(NoteContract.Intent.ChangeBack(0))
      else -> onClick(NoteContract.Intent.ChangeBack(index))
    }
  }) {
    Image(painter = painterResource(id = icon), contentDescription = null)
  }
}