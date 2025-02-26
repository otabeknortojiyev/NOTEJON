package uz.gita.otabek.notejon.screens.home

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.notejon.R
import uz.gita.otabek.notejon.screens.home.items.AppInfoDialog
import uz.gita.otabek.notejon.screens.home.items.NoteItem
import uz.gita.otabek.notejon.ui.theme.MainGray


class HomeScreen : Screen {
  @Composable
  override fun Content() {
    val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
    val uiState = viewModel.collectAsState()
    Content(uiState, viewModel::onEvent)
  }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
private fun Content(
  uiState: State<HomeContract.UiState>, onEvent: (HomeContract.Intent) -> Unit,
) {
  var isClickable by remember { mutableStateOf(true) }
  val openAlertDialog = remember { mutableStateOf(false) }
  var openBottomSheet by rememberSaveable { mutableStateOf(false) }
  val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
  LaunchedEffect(openBottomSheet) {
    if (openBottomSheet) {
      coroutineScope.launch {
        bottomSheetState.show()
      }
    } else {
      coroutineScope.launch {
        bottomSheetState.hide()
      }
    }
  }
  val pullRefreshState = rememberPullRefreshState(refreshing = uiState.value.isLoading,
    onRefresh = { /*onEventDispatcher(HomeContract.Intent.Init)*/ })
  LaunchedEffect(Unit) {
    onEvent(HomeContract.Intent.Init)
  }
  Box(
    modifier = Modifier
      .fillMaxSize()
      .pullRefresh(pullRefreshState)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(color = MainGray), horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .shadow(2.dp)
          .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "NOTEJON",
          color = Color.Black,
          fontSize = 24.sp,
          fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
          modifier = Modifier.padding(20.dp)
        )
        Row {
          Image(painter = painterResource(id = R.drawable.info),
            contentDescription = null,
            modifier = Modifier
              .clip(shape = RoundedCornerShape(10.dp))
              .clickable {
                openAlertDialog.value = true
              }
              .padding(20.dp))
          Image(painter = painterResource(id = R.drawable.add_note),
            contentDescription = null,
            modifier = Modifier
              .clip(shape = RoundedCornerShape(10.dp))
              .clickable {
                if (isClickable) {
                  isClickable = false
                  coroutineScope.launch {
                    onEvent(HomeContract.Intent.MoveToAdd)
                    delay(500)
                    isClickable = true
                  }
                }
              }
              .padding(20.dp))
        }
        if (openAlertDialog.value) {
          AppInfoDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
              openAlertDialog.value = false
            },
            dialogTitle = "NOTEJON",
            dialogText = "I made this application as homework when I was studying at GITA Academy. Here you can save notes. Basic knowledge that was useful to me here: Compose, Hilt, Voyager, Orbit, Room.",
            icon = R.drawable.info
          )
        }
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = "All", fontFamily = if (uiState.value.isAll) {
          FontFamily(Font(R.font.montserrat_medium))
        } else {
          FontFamily(Font(R.font.montserrat_light))
        }, modifier = Modifier
          .weight(1f)
          .padding(10.dp)
          .border(
            1.dp, color = if (uiState.value.isAll) {
              Color.Blue
            } else {
              Color.White
            }, shape = RoundedCornerShape(10.dp)
          )
          .clip(shape = RoundedCornerShape(10.dp))
          .background(color = Color.White)
          .clickable {
            onEvent(HomeContract.Intent.Init)
          }
          .padding(10.dp), textAlign = TextAlign.Center)
        Text(text = "By tags", fontFamily = if (uiState.value.isByTags) {
          FontFamily(Font(R.font.montserrat_medium))
        } else {
          FontFamily(Font(R.font.montserrat_light))
        }, modifier = Modifier
          .weight(1f)
          .padding(10.dp)
          .border(
            1.dp, color = if (uiState.value.isByTags) {
              Color.Blue
            } else {
              Color.White
            }, shape = RoundedCornerShape(10.dp)
          )
          .clip(shape = RoundedCornerShape(10.dp))
          .background(color = Color.White)
          .clickable {
            onEvent(HomeContract.Intent.LoadTags)
            openBottomSheet = true
          }
          .padding(10.dp), textAlign = TextAlign.Center)
        Text(text = "Favorite", fontFamily = if (uiState.value.isFavorite) {
          FontFamily(Font(R.font.montserrat_medium))
        } else {
          FontFamily(Font(R.font.montserrat_light))
        }, modifier = Modifier
          .weight(1f)
          .padding(10.dp)
          .border(
            1.dp, color = if (uiState.value.isFavorite) {
              Color.Blue
            } else {
              Color.White
            }, shape = RoundedCornerShape(10.dp)
          )
          .clip(shape = RoundedCornerShape(10.dp))
          .background(color = Color.White)
          .clickable {
            onEvent(HomeContract.Intent.LoadFavNotes)
          }
          .padding(10.dp), textAlign = TextAlign.Center)
      }
      if (uiState.value.notes.isEmpty()) {
        Image(
          painter = painterResource(id = R.drawable.empty_notes),
          contentDescription = null,
          modifier = Modifier
            .weight(1f)
            .align(alignment = Alignment.CenterHorizontally)
        )
      } else {
        LazyColumn(modifier = Modifier.weight(1f)) {
          itemsIndexed(uiState.value.notes, key = { _, note -> note.id }) { index, item ->
            NoteItem(
              data = item,
              onEventDispatcher = onEvent,
              changeFav = HomeContract.Intent.UpdateFavNote(item.copy(favorite = !item.favorite), index),
              modifier = Modifier.animateItem(
                fadeOutSpec = tween(1000), placementSpec = spring(stiffness = Spring.StiffnessMediumLow)
              )
            )
          }
        }
      }
    }
    PullRefreshIndicator(
      refreshing = uiState.value.isLoading,
      state = pullRefreshState,
      modifier = Modifier.align(Alignment.TopCenter),
      backgroundColor = if (uiState.value.isLoading) Color.Red else Color.Green,
    )
  }
  if (openBottomSheet) {
    ModalBottomSheet(onDismissRequest = { openBottomSheet = false }, sheetState = bottomSheetState) {
      Text(
        text = "Tags",
        color = Color.Black,
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )
      if (uiState.value.tags.isNotEmpty()) {
        LazyColumn {
          itemsIndexed(uiState.value.tags) { index, item ->
            Row(modifier = Modifier
              .fillMaxWidth()
              .padding(
                start = 16.dp, end = 16.dp, top = 16.dp, bottom = if (index != uiState.value.tags.size - 1) {
                  0.dp
                } else {
                  64.dp
                }
              )
              .clip(shape = RoundedCornerShape(10.dp))
              .background(color = Color.White)
              .clickable {
                openBottomSheet = false
                onEvent(HomeContract.Intent.LoadNotesByTag(item.name))
              }
              .padding(10.dp)) {
              Text(
                text = ("#" + item.name).uppercase(), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
              )
            }
          }
        }
      }
    }
  }

  val systemUiController = rememberSystemUiController()
  systemUiController.setStatusBarColor(Color.White)
  systemUiController.setNavigationBarColor(Color.White)
}
