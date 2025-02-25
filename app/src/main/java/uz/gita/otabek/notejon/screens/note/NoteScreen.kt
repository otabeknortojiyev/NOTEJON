package uz.gita.otabek.notejon.screens.note

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextFieldDefaults
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
import uz.gita.otabek.notejon.utils.AlignEnum
import uz.gita.otabek.notejon.utils.AppTextField


class NoteScreen(val data: NoteData? = null) : Screen {

    @Composable
    override fun Content() {
        val viewModel: NoteContract.ViewModel = getViewModel<NoteViewModel>()
        val uiState = viewModel.collectAsState()
        NoteScreenContent(uiState, viewModel::onEventDispatcher, data)
    }
}

@SuppressLint("ResourceAsColor")
@Composable
private fun NoteScreenContent(
    uiState: State<NoteContract.UiState>, onEventDispatcher: (NoteContract.Intent) -> Unit, data: NoteData?,
) {
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
    val tag = remember {
        mutableStateOf(data?.tag ?: "")
    }
    LaunchedEffect(Unit) {
        if (data != null) {
            onEventDispatcher(NoteContract.Intent.SetProperties(data))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainGray)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.left), contentDescription = null, modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable {
                    onEventDispatcher(NoteContract.Intent.Back)
                }
                .padding(20.dp))
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = if (uiState.value.favorite) {
                painterResource(id = R.drawable.favorite_yellow)
            } else {
                painterResource(id = R.drawable.favorite_gray)
            }, contentDescription = null, modifier = Modifier
                .padding(horizontal = 10.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .clickable {
                    onEventDispatcher(NoteContract.Intent.ChangeFavorite(!uiState.value.favorite))
                })
            Image(painter = painterResource(id = R.drawable.blue), contentDescription = null, modifier = Modifier
                .padding(horizontal = 10.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .clickable {
                    if (uiState.value.backgroundColor == 1) {
                        onEventDispatcher(NoteContract.Intent.ChangeBack(0))
                    } else if (uiState.value.backgroundColor == 0 || uiState.value.backgroundColor != 1) {
                        onEventDispatcher(NoteContract.Intent.ChangeBack(1))
                    }
                })
            Image(painter = painterResource(id = R.drawable.red), contentDescription = null, modifier = Modifier
                .padding(horizontal = 10.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .clickable {
                    if (uiState.value.backgroundColor == 2) {
                        onEventDispatcher(NoteContract.Intent.ChangeBack(0))
                    } else if (uiState.value.backgroundColor == 0 || uiState.value.backgroundColor != 2) {
                        onEventDispatcher(NoteContract.Intent.ChangeBack(2))
                    }
                })
            Image(painter = painterResource(id = R.drawable.green), contentDescription = null, modifier = Modifier
                .padding(horizontal = 10.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .clickable {
                    if (uiState.value.backgroundColor == 3) {
                        onEventDispatcher(NoteContract.Intent.ChangeBack(0))
                    } else if (uiState.value.backgroundColor == 0 || uiState.value.backgroundColor != 3) {
                        onEventDispatcher(NoteContract.Intent.ChangeBack(3))
                    }
                })
            Image(painter = painterResource(id = R.drawable.yellow),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .clickable {
                        if (uiState.value.backgroundColor == 4) {
                            onEventDispatcher(NoteContract.Intent.ChangeBack(0))
                        } else if (uiState.value.backgroundColor == 0 || uiState.value.backgroundColor != 4) {
                            onEventDispatcher(NoteContract.Intent.ChangeBack(4))
                        }
                    })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AppTextField(
                value = title.value,
                onValueChange = {
                    if (it.length < 20) title.value = it
                },
                hint = "Title",
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                modifier = Modifier
                    .padding(start = 20.dp, end = 10.dp)
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                )
            )
            AppTextField(
                value = tag.value,
                onValueChange = {
                    if (it.length < 20 && !it.contains(" ")) tag.value = it
                },
                hint = "Tag",
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(10.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.paint_gray), contentDescription = null, modifier = Modifier
                    .padding(end = 5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable {
                        if (uiState.value.textColor == 1) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextColor(0))
                        } else if (uiState.value.textColor == 0 || uiState.value.textColor != 1) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextColor(1))
                        }
                    })
                Image(painter = painterResource(id = R.drawable.paint_yellow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable {
                            if (uiState.value.textColor == 2) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextColor(0))
                            } else if (uiState.value.textColor == 0 || uiState.value.textColor != 2) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextColor(2))
                            }
                        })
                Image(painter = painterResource(id = R.drawable.paint_blue),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable {
                            if (uiState.value.textColor == 3) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextColor(0))
                            } else if (uiState.value.textColor == 0 || uiState.value.textColor != 3) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextColor(3))
                            }
                        })
                Image(painter = painterResource(id = R.drawable.paint_red),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable {
                            if (uiState.value.textColor == 4) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextColor(0))
                            } else if (uiState.value.textColor == 0 || uiState.value.textColor != 4) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextColor(4))
                            }
                        })
                Image(painter = painterResource(id = R.drawable.paint_green), contentDescription = null, modifier = Modifier
                    .padding(start = 5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable {
                        if (uiState.value.textColor == 5) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextColor(0))
                        } else if (uiState.value.textColor == 0 || uiState.value.textColor != 5) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextColor(5))
                        }
                    })
            }
            Row(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(10.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.text_left), contentDescription = null, modifier = Modifier
                    .padding(end = 5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable {
                        if (uiState.value.textAlign == 1) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextAlign(0))
                        } else if (uiState.value.textAlign == 0 || uiState.value.textAlign != 1) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextAlign(1))
                        }
                    })
                Image(painter = painterResource(id = R.drawable.text_center),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable {
                            if (uiState.value.textAlign == 2) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextAlign(0))
                            } else if (uiState.value.textAlign == 0 || uiState.value.textAlign != 2) {
                                onEventDispatcher(NoteContract.Intent.ChangeTextAlign(2))
                            }
                        })
                Image(painter = painterResource(id = R.drawable.text_right), contentDescription = null, modifier = Modifier
                    .padding(start = 5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable {
                        if (uiState.value.textAlign == 3) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextAlign(0))
                        } else if (uiState.value.textAlign == 0 || uiState.value.textAlign != 3) {
                            onEventDispatcher(NoteContract.Intent.ChangeTextAlign(3))
                        }
                    })
            }
            Image(painter = painterResource(id = R.drawable.save), contentDescription = null, modifier = Modifier.clickable {
                if (text.value.isNotEmpty()) {
                    onEventDispatcher(NoteContract.Intent.SaveNote(NoteData(data?.id ?: 0, title.value.ifEmpty {
                        "Заголовок"
                    }, text.value, System.currentTimeMillis(), uiState.value.favorite, tag.value.ifEmpty { "" }, when (uiState.value.textColor) {
                        0 -> {
                            TextBlack.value.toLong()
                        }

                        1 -> {
                            TextGray.value.toLong()
                        }

                        2 -> {
                            TextYellow.value.toLong()
                        }

                        3 -> {
                            TextBlue.value.toLong()
                        }

                        4 -> {
                            TextRed.value.toLong()
                        }

                        else -> {
                            TextGreen.value.toLong()
                        }
                    }, when (uiState.value.backgroundColor) {
                        0 -> {
                            Color.White.value.toLong()
                        }

                        1 -> {
                            BackBlue.value.toLong()
                        }

                        2 -> {
                            BackRed.value.toLong()
                        }

                        3 -> {
                            BackGreen.value.toLong()
                        }

                        else -> {
                            BackYellow.value.toLong()
                        }
                    }, when (uiState.value.textAlign) {
                        0 -> {
                            AlignEnum.JUSTIFY.value
                        }

                        1 -> {
                            AlignEnum.LEFT.value
                        }

                        2 -> {
                            AlignEnum.CENTER.value
                        }

                        else -> {
                            AlignEnum.RIGHT.value
                        }
                    }
                    )
                    )
                    )
                } else {
                    onEventDispatcher(NoteContract.Intent.Error(true))
                }
            })
        }
        Column(modifier = Modifier.weight(1f)) {
            AppTextField(
                value = text.value, errorText = if (uiState.value.error) {
                    "Field must not be empty"
                } else {
                    ""
                }, onValueChange = {
                    onEventDispatcher(NoteContract.Intent.Error(false))
                    text.value = it
                }, singleLine = false, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = when (uiState.value.backgroundColor) {
                        0 -> {
                            Color.White
                        }

                        1 -> {
                            BackBlue
                        }

                        2 -> {
                            BackRed
                        }

                        3 -> {
                            BackGreen
                        }

                        else -> {
                            BackYellow
                        }
                    }
                ), textStyle = TextStyle(
                    color = when (uiState.value.textColor) {
                        0 -> {
                            TextBlack
                        }

                        1 -> {
                            TextGray
                        }

                        2 -> {
                            TextYellow
                        }

                        3 -> {
                            TextBlue
                        }

                        4 -> {
                            TextRed
                        }

                        else -> {
                            TextGreen
                        }
                    }, fontSize = 24.sp
                ), textAlign = when (uiState.value.textAlign) {
                    0 -> {
                        TextAlign.Justify
                    }

                    1 -> {
                        TextAlign.Start
                    }

                    2 -> {
                        TextAlign.Center
                    }

                    else -> {
                        TextAlign.End
                    }
                }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.None), modifier = Modifier.padding(20.dp)
            )
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.setNavigationBarColor(Color.White)
}