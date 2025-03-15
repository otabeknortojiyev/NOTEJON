package uz.gita.otabek.notejon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.otabek.notejon.ui.navigation.AppNavigationDispatcher
import uz.gita.otabek.notejon.ui.navigation.AppNavigator
import uz.gita.otabek.notejon.ui.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
  @Binds
  fun bindAppNavigator(dispatcher: AppNavigationDispatcher): AppNavigator

  @Binds
  fun bindNavigationHandler(dispatcher: AppNavigationDispatcher): NavigationHandler
}