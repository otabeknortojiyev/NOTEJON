package uz.gita.otabek.notejon.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.notejon.R
import uz.gita.otabek.notejon.screens.home.items.NoteItem
import uz.gita.otabek.notejon.screens.home.items.SelectableTextButton
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
private fun Content(uiState: State<HomeContract.UiState>, onEvent: (HomeContract.Intent) -> Unit) {
  LaunchedEffect(Unit) { onEvent(HomeContract.Intent.Init) }

  var isClickable by remember { mutableStateOf(true) }
  val pullRefreshState = rememberPullRefreshState(refreshing = uiState.value.isLoading, onRefresh = {
    onEvent(HomeContract.Intent.Init)
  })

  Box(
    modifier = Modifier
      .fillMaxSize()
      .pullRefresh(pullRefreshState)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(color = MainGray)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "NOTEJON",
          color = Color.Black,
          fontSize = 24.sp,
          fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
          modifier = Modifier.padding(start = 16.dp)
        )
        IconButton(onClick = {
          if (isClickable) {
            isClickable = false
            onEvent(HomeContract.Intent.MoveToAdd())
          }
        }) {
          Image(painter = painterResource(id = R.drawable.add_note), contentDescription = null)
        }
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        SelectableTextButton("All", uiState.value.isAll, Modifier.weight(1f)) {
          onEvent(HomeContract.Intent.Init)
        }
        SelectableTextButton("Favorite", uiState.value.isFavorite, Modifier.weight(1f)) {
          onEvent(HomeContract.Intent.LoadFavNotes)
        }
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
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          itemsIndexed(uiState.value.notes, key = { _, note -> note.id }) { index, item ->
            NoteItem(
              data = item, onEvent = onEvent, modifier = Modifier.animateItem(
                fadeOutSpec = tween(500), placementSpec = spring(stiffness = Spring.StiffnessMediumLow)
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

  val systemUiController = rememberSystemUiController()
  systemUiController.setStatusBarColor(Color.White)
  systemUiController.setNavigationBarColor(Color.White)
}