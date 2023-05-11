package kz.jcourier.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
import kz.jcourier.data.interceptors.RequestHeaderInterceptor
import kz.jcourier.data.interceptors.TokenAuthenticator
import kz.jcourier.data.sharedprefs.SharedPreferencesProvider
import kz.jcourier.data.sharedprefs.SharedPreferencesProviderImpl
import okhttp3.ConnectionSpec
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicOkHttpClient

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
    fun provideAuthInterceptor(sharedPreferencesProvider: SharedPreferencesProvider): AuthInterceptor =
        AuthInterceptor(sharedPreferencesProvider)

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideActivitySharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideSharedPrefProvider(
        gson: Gson,
        prefs: SharedPreferences,
    ): SharedPreferencesProvider {
        return SharedPreferencesProviderImpl(
            gson = gson,
            sharedPreferences = prefs
        )
    }

    @Singleton
    @BasicOkHttpClient
    @Provides
    fun provideOkHttpClientWithoutInterceptors(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                requestBuilder.header("accept", "*/*")
                chain.proceed(requestBuilder.build())
            }
            .writeTimeout(100, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    addNetworkInterceptor(logger)
                }
            }
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            .readTimeout(100, TimeUnit.SECONDS).build()
    }

    @Singleton
    @AuthInterceptorOkHttpClient
    @Provides
    fun provideOkHttp(
        authInterceptor: RequestHeaderInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder();
        okHttpClient.callTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okHttpClient.addInterceptor(authInterceptor)
        okHttpClient.authenticator(tokenAuthenticator)
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
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
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