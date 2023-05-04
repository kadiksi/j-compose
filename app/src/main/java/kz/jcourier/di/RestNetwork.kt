package kz.jcourier.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kz.jcourier.BuildConfig
import kz.jcourier.data.api.LoginApiService
import kz.jcourier.data.interceptors.AuthInterceptor
import kz.jcourier.data.interceptors.TokenManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
object RestNetwork {

    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return BuildConfig.HOST
    }

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideOkHttp(authInterceptor: AuthInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder();
        okHttpClient.callTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okHttpClient.addInterceptor(authInterceptor)
        return okHttpClient.build();
    }
//
//    I/okhttp.OkHttpClient: --> POST https://test5.jmart.kz/gw/user/v1/auth/sign-in
//    I/okhttp.OkHttpClient: Content-Type: application/json; charset=UTF-8
//    I/okhttp.OkHttpClient: Content-Length: 69
//    I/okhttp.OkHttpClient: {"login":"+7(123)123-12-31","password":"12312312312","user_type":"K"}
//    I/okhttp.OkHttpClient: --> END POST (69-byte body)

    @Singleton
    @Provides
    fun provideRestAdapter(
        baseURL: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retro = Retrofit.Builder().baseUrl(baseURL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();
        return retro;
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }


    @Provides
    fun provideIDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }


}