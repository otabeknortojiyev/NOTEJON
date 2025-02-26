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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.otabek.notejon.R
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.screens.home.HomeContract
import uz.gita.otabek.notejon.utils.longToDateString

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun NoteItem(
  data: NoteData,
  onEventDispatcher: (HomeContract.Intent) -> Unit,
  changeFav: HomeContract.Intent,
  modifier: Modifier,
) {
  var isClickable by remember { mutableStateOf(true) }
  val coroutineScope = rememberCoroutineScope()
  val trash = remember {
    mutableStateOf(false)
  }
  val expand = remember {
    mutableStateOf(false)
  }
  val defaultModifier = Modifier
    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 4.dp)
    .fillMaxWidth()
    .clip(shape = RoundedCornerShape(10.dp))
    .background(color = Color(data.backgroundColor.toULong()))
    .combinedClickable(onLongClick = {
      trash.value = true
    }, onClick = {
      if (isClickable) {
        isClickable = false
        coroutineScope.launch {
          onEventDispatcher(HomeContract.Intent.Edit(data = data))
          delay(500)
          isClickable = true
        }
      }
    })
  Column(defaultModifier.then(modifier)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row {
        Text(
          text = data.title, modifier = Modifier.padding(start = 20.dp, top = 8.dp), fontSize = 24.sp
        )
        Image(painter = if (data.favorite) {
          painterResource(id = R.drawable.favorite_yellow)
        } else {
          painterResource(id = R.drawable.favorite_gray)
        }, contentDescription = null, modifier = Modifier
          .clip(shape = RoundedCornerShape(20.dp))
          .clickable {
            onEventDispatcher(changeFav)
          }
          .padding(10.dp))
      }
      Row {
        Image(painter = if (!expand.value) {
          painterResource(id = R.drawable.expand)
        } else {
          painterResource(id = R.drawable.minimize)
        }, contentDescription = null, modifier = Modifier
          .clip(shape = RoundedCornerShape(20.dp))
          .clickable {
            expand.value = !expand.value
          }
          .padding(10.dp))
      }
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = data.text,
        color = Color(data.textColor.toULong()),
        modifier = Modifier.weight(2f),
        maxLines = if (expand.value) {
          data.text.length
        } else {
          3
        },
        overflow = TextOverflow.Ellipsis
      )
    }
    Spacer(
      modifier = Modifier
        .fillMaxWidth()
        .height(20.dp)
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (data.tag.isNotEmpty()) {
          "#" + data.tag.uppercase()
        } else {
          ""
        }, color = Color.Gray, fontSize = 16.sp, modifier = Modifier.padding(10.dp)
      )
      Text(
        text = data.date.longToDateString(), color = Color.Black, fontSize = 16.sp, modifier = Modifier.padding(10.dp)
      )
    }
  }
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (trash.value) {
      Image(painter = painterResource(id = R.drawable.trash),
        contentDescription = null,
        modifier = Modifier
          .clip(shape = RoundedCornerShape(20.dp))
          .background(color = Color.White)
          .clickable {
            onEventDispatcher(HomeContract.Intent.DeleteNote(data = data))
          }
          .padding(vertical = 8.dp, horizontal = 16.dp))
      Image(painter = painterResource(id = R.drawable.close),
        contentDescription = null,
        modifier = Modifier
          .clip(shape = RoundedCornerShape(10.dp))
          .clickable {
            trash.value = false
          }
          .padding(10.dp))
    }
  }
}