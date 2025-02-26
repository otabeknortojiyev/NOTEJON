package uz.gita.otabek.notejon.screens.home.items

import androidx.compose.foundation.Image
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import uz.gita.otabek.notejon.R

@Composable
fun AppInfoDialog(
  onDismissRequest: () -> Unit,
  onConfirmation: () -> Unit,
  dialogTitle: String,
  dialogText: String,
  icon: Int,
) {
  AlertDialog(icon = {
    Image(painter = painterResource(id = icon), contentDescription = null)
  }, title = {
    Text(text = dialogTitle, fontFamily = FontFamily(Font(R.font.montserrat_semibold)))
  }, text = {
    Text(text = dialogText, fontFamily = FontFamily(Font(R.font.montserrat_medium)))
  }, onDismissRequest = {
    onDismissRequest()
  }, confirmButton = {
    TextButton(onClick = {
      onConfirmation()
    }) {
      Text("OK")
    }
  })
}