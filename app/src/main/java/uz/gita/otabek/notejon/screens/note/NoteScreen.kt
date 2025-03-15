package uz.gita.otabek.notejon.screens.note

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.notejon.R
import uz.gita.otabek.notejon.data.model.NoteData
import uz.gita.otabek.notejon.screens.note.items.BackgroundColorItem
import uz.gita.otabek.notejon.screens.note.items.TextAlignItem
import uz.gita.otabek.notejon.screens.note.items.TextColorItem
import uz.gita.otabek.notejon.ui.theme.BackBlue
import uz.gita.otabek.notejon.ui.theme.BackGreen
import uz.gita.otabek.notejon.ui.theme.BackRed
import uz.gita.otabek.notejon.ui.theme.BackYellow
import uz.gita.otabek.notejon.ui.theme.MainGray
import uz.gita.otabek.notejon.ui.theme.TextBlack
import uz.gita.otabek.notejon.ui.theme.TextBlue
import uz.gita.otabek.notejon.ui.theme.TextGray
import uz.gita.otabek.notejon.ui.theme.TextGreen
import uz.gita.otabek.notejon.ui.theme.TextRed
import uz.gita.otabek.notejon.ui.theme.TextYellow
import uz.gita.otabek.notejon.utils.AppTextField

class NoteScreen(val data: NoteData? = null) : Screen {
  @Composable
  override fun Content() {
    val viewModel: NoteContract.ViewModel = getViewModel<NoteViewModel>()
    val uiState = viewModel.collectAsState()
    Content(uiState, viewModel::onEvent, data)
  }
}

@SuppressLint("ResourceAsColor")
@Composable
private fun Content(
  uiState: State<NoteContract.UiState>, onEvent: (NoteContract.Intent) -> Unit, data: NoteData?,
) {
  val backgroundColors = listOf(
    R.drawable.blue, R.drawable.red, R.drawable.green, R.drawable.yellow
  )

  val textColors = listOf(
    R.drawable.paint_gray, R.drawable.paint_yellow, R.drawable.paint_blue, R.drawable.paint_red, R.drawable.paint_green
  )

  val textAligns = listOf(
    R.drawable.text_left, R.drawable.text_center, R.drawable.text_right
  )
  val text = remember {
    mutableStateOf(data?.text ?: "")
  }
  val title = remember {
    mutableStateOf(
      if (data != null && data.title != "Заголовок") {
        data.title
      } else {
        ""
      }
    )
  }
  LaunchedEffect(Unit) {
    if (data != null) {
      onEvent(NoteContract.Intent.SetProperties(data))
    }
  }
  Scaffold(topBar = {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White), verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = { onEvent(NoteContract.Intent.Back) }) {
        Image(painter = painterResource(id = R.drawable.left), contentDescription = null)
      }
      Spacer(modifier = Modifier.weight(1f))
      IconButton(onClick = { onEvent(NoteContract.Intent.ChangeFavorite(!uiState.value.favorite)) }) {
        Image(
          painter = painterResource(
            id = if (uiState.value.favorite) R.drawable.favorite_yellow else R.drawable.favorite_gray
          ), contentDescription = null
        )
      }
      backgroundColors.forEachIndexed { index, icon ->
        BackgroundColorItem(index = index + 1, icon = icon, backgroundColor = uiState.value.backgroundColor) {
          onEvent(it)
        }
      }
    }
  }, floatingActionButton = {
    IconButton(modifier = Modifier
      .clip(shape = CircleShape)
      .background(color = Color.White), onClick = {
      if (text.value.isNotEmpty()) {
        onEvent(
          NoteContract.Intent.SaveNote(
            NoteData(
              data?.id ?: 0, title.value.ifEmpty {
                "Заголовок"
              }, text.value, System.currentTimeMillis(), uiState.value.favorite, when (uiState.value.textColor) {
                0 -> TextBlack.value.toLong()
                1 -> TextGray.value.toLong()
                2 -> TextYellow.value.toLong()
                3 -> TextBlue.value.toLong()
                4 -> TextRed.value.toLong()
                else -> TextGreen.value.toLong()
              }, when (uiState.value.backgroundColor) {
                0 -> Color.White.value.toLong()
                1 -> BackBlue.value.toLong()
                2 -> BackRed.value.toLong()
                3 -> BackGreen.value.toLong()
                else -> BackYellow.value.toLong()
              }, when (uiState.value.textAlign) {
                0 -> 0
                1 -> 1
                2 -> 2
                else -> 3
              }
            )
          )
        )
      } else {
        onEvent(NoteContract.Intent.Error(true))
      }
    }) {
      Image(painter = painterResource(id = R.drawable.save), contentDescription = null)
    }

  }) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(color = MainGray)
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically
      ) {
        AppTextField(
          value = title.value,
          onValueChange = { if (it.length < 20) title.value = it },
          hint = "Title",
          singleLine = true,
          textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
          keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
          modifier = Modifier
            .padding(horizontal = 16.dp)
            .weight(1f),
          colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )
      }
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 16.dp, top = 16.dp)
      ) {
        Row(modifier = Modifier.background(color = Color.White, shape = CircleShape)) {
          textColors.forEachIndexed { index, icon ->
            TextColorItem(index = index + 1, icon = icon, textColor = uiState.value.textColor) {
              onEvent(it)
            }
          }
        }
      }
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 16.dp, top = 16.dp)
      ) {
        Row(modifier = Modifier.background(color = Color.White, shape = CircleShape)) {
          textAligns.forEachIndexed { index, icon ->
            TextAlignItem(index = index + 1, icon = icon, textAlign = uiState.value.textAlign) {
              onEvent(it)
            }
          }
        }
      }
      Column(modifier = Modifier.weight(1f)) {
        AppTextField(
          value = text.value,
          errorText = if (uiState.value.error) "Field must not be empty" else "",
          onValueChange = {
            onEvent(NoteContract.Intent.Error(false))
            text.value = it
          },
          singleLine = false,
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = when (uiState.value.backgroundColor) {
              0 -> Color.White
              1 -> BackBlue
              2 -> BackRed
              3 -> BackGreen
              else -> BackYellow
            }
          ),
          textStyle = TextStyle(
            color = when (uiState.value.textColor) {
              0 -> TextBlack
              1 -> TextGray
              2 -> TextYellow
              3 -> TextBlue
              4 -> TextRed
              else -> TextGreen
            }, fontSize = 24.sp
          ),
          textAlign = when (uiState.value.textAlign) {
            0 -> TextAlign.Justify
            1 -> TextAlign.Start
            2 -> TextAlign.Center
            else -> TextAlign.End
          },
          keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.None),
          modifier = Modifier.padding(16.dp)
        )
      }
    }
  }
  val systemUiController = rememberSystemUiController()
  systemUiController.setStatusBarColor(Color.White)
  systemUiController.setNavigationBarColor(Color.White)
}