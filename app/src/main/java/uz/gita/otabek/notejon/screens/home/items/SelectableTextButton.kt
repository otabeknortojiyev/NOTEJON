package uz.gita.otabek.notejon.screens.home.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.gita.otabek.notejon.R

@Composable
fun SelectableTextButton(
  text: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit,
) {
  Text(text = text,
    fontFamily = FontFamily(
      Font(
        if (isSelected) R.font.montserrat_medium else R.font.montserrat_light
      )
    ),
    modifier = modifier
      .commonTextModifier(isSelected)
      .clickable { onClick() }
      .padding(10.dp),
    textAlign = TextAlign.Center)
}

fun Modifier.commonTextModifier(isSelected: Boolean) =
  this
    .border(1.dp, if (isSelected) Color.Blue else Color.White, CircleShape)
    .clip(CircleShape)
    .background(Color.White)