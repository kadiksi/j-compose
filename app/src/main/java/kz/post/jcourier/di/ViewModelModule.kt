package kz.post.jcourier.di

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.post.jcourier.viewmodel.IsLogin
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun provideMySingleton(): MutableState<IsLogin> = mutableStateOf(IsLogin(false))
}