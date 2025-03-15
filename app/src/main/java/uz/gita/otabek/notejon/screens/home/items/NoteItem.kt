package uz.gita.otabek.notejon.screens.home.items

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.otabek.notejon.R
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.screens.home.HomeContract
import uz.gita.otabek.notejon.utils.longToDateString

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun NoteItem(
  data: NoteData,
  onEvent: (HomeContract.Intent) -> Unit,
  modifier: Modifier,
) {
  val isClickable = remember { mutableStateOf(true) }
  val trash = remember { mutableStateOf(false) }
  val expand = remember { mutableStateOf(false) }
  val defaultModifier = Modifier
    .padding(horizontal = 16.dp)
    .fillMaxWidth()
    .clip(shape = RoundedCornerShape(12.dp))
    .background(color = Color(data.backgroundColor.toULong()))
    .combinedClickable(onLongClick = {
      trash.value = true
    }, onClick = {
      if (isClickable.value) {
        isClickable.value = false
        onEvent(HomeContract.Intent.MoveToAdd(data = data))
      }
    })
  Column(defaultModifier.then(modifier)) {
    Row(
      modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = data.title, fontSize = 24.sp, modifier = Modifier.padding(start = 20.dp))
        Image(painter = if (data.favorite) {
          painterResource(id = R.drawable.favorite_yellow)
        } else {
          painterResource(id = R.drawable.favorite_gray)
        }, contentDescription = null, modifier = Modifier
          .clip(shape = CircleShape)
          .clickable {
            onEvent(HomeContract.Intent.UpdateFavNote(data.copy(favorite = !data.favorite)))
          }
          .padding(10.dp))
      }
      Spacer(modifier = Modifier.weight(1f))
      IconButton(onClick = { expand.value = !expand.value }) {
        Image(
          painter = if (!expand.value) {
            painterResource(id = R.drawable.expand)
          } else {
            painterResource(id = R.drawable.minimize)
          }, contentDescription = null
        )
      }
    }
    Row(modifier = Modifier.fillMaxWidth()) {
      Text(
        modifier = Modifier.padding(start = 12.dp),
        text = data.text,
        color = Color(data.textColor.toULong()),
        maxLines = if (expand.value) {
          data.text.length
        } else {
          3
        },
        overflow = TextOverflow.Ellipsis
      )
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = data.date.longToDateString(),
        color = Color.Black,
        fontSize = 16.sp,
        modifier = Modifier.padding(end = 10.dp)
      )
    }
  }
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (trash.value) {
      IconButton(onClick = { onEvent(HomeContract.Intent.DeleteNote(data = data)) }) {
        Image(
          painter = painterResource(
            id = R.drawable.trash
          ), contentDescription = null
        )
      }
      IconButton(onClick = { trash.value = false }) {
        Image(
          painter = painterResource(id = R.drawable.close), contentDescription = null
        )
      }
    }
  }
}