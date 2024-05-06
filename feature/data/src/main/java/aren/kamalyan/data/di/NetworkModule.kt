package aren.kamalyan.data.di

import android.content.Context
import aren.kamalyan.data.BuildConfig
import aren.kamalyan.data.network.api.GithubApi
import aren.kamalyan.domain.persistent.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_CONNECTION_SECONDS = 30L
    private const val TIMEOUT_READ_SECONDS = 30L
    private const val TIMEOUT_WRITE_SECONDS = 30L

    @Provides
    @Singleton
    fun provideRetrofit(
        jsonConfigs: Json,
        prefManager: PrefManager,
    ): Retrofit = createRetrofit(jsonConfigs)

    @Singleton
    @Named("Normal")
    @Provides
    fun provideOkHttpClient(
//        exceptionInterceptor: ExceptionInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        json: Json,
        prefManager: PrefManager,
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .followSslRedirects(true)
            .connectTimeout(TIMEOUT_CONNECTION_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE_SECONDS, TimeUnit.SECONDS)
            .cache(cache)
//            .addInterceptor(exceptionInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Named("Secure")
    @Provides
    fun provideSecureOkHttpClient(
        @Named("Normal") okHttpClient: OkHttpClient,
//        authInterceptor: AuthRequestInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .also { okHttpBuilder ->
                //NOTE: to avoid duplicate logging
                okHttpBuilder.interceptors().removeIf { it is HttpLoggingInterceptor }
            }
//            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideJsonConfigurations(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = true
    }

    private fun createRetrofit(
        json: Json
    ): Retrofit {
        val converterFactory = json.asConverterFactory("application/json".toMediaType())

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

    private inline fun <reified T> createApiService(
        retrofit: Retrofit,
        okHttpClient: OkHttpClient
    ): T {
        return retrofit.newBuilder().client(okHttpClient).build().create(T::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideNetworkInterceptor(@ApplicationContext context: Context): Interceptor {
//        return Interceptor { chain: Interceptor.Chain ->
//            val request = chain.request()
//            val newRequest = if (context.hasNetworkConnection()) {
//                request.newBuilder()
//                    .header("Cache-Control", "public, max-age=" + 60)
//                    .build()
//            } else {
//                request.newBuilder()
//                    .header(
//                        "Cache-Control",
//                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
//                    )
//                    .build()
//            }
//            chain.proceed(newRequest)
//        }
//    }

    @Singleton
    @Provides
    fun provideGithubApi(
        retrofit: Retrofit,
        @Named("Normal") okHttpClient: OkHttpClient
    ): GithubApi =
        createApiService(retrofit, okHttpClient)

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }
}
