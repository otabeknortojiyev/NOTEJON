package uz.gita.otabek.notejon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.otabek.notejon.screens.home.HomeScreen
import uz.gita.otabek.notejon.ui.navigation.NavigationHandler
import uz.gita.otabek.notejon.ui.theme.NOTEJONTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOTEJONTheme {
                Navigator(screen = HomeScreen(), onBackPressed = {
                    true
                }) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigationHandler.screenState.onEach {
                            it.invoke(navigator)
                        }.launchIn(lifecycleScope)
                    }
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}