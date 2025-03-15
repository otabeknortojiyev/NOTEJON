package uz.gita.otabek.notejon.ui.navigation

import cafe.adriel.voyager.core.screen.Screen

typealias AppScreen = Screen

interface AppNavigator {
  suspend fun back()
  suspend fun push(screen: AppScreen)
  suspend fun popUntil(screen: AppScreen)
  suspend fun replace(screen: AppScreen)
  suspend fun replaceAll(screen: AppScreen)
  suspend fun backToRoot()
}