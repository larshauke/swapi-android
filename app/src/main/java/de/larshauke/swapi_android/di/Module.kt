package de.larshauke.swapi_android.di

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.larshauke.swapi_android.networking.adapter.DateAdapter
import de.larshauke.swapi_android.networking.adapter.ZonedDateTimeAdapter
import de.larshauke.swapi_android.networking.interceptor.ErrorInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor {
            Log.d("OkHttp", it)
        }.apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }


    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(ZonedDateTimeAdapter())
            .add(DateAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideCacheInterceptor(): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())

        // Set cache duration based on server's response
        response.newBuilder()
            .header("Cache-Control", "public, max-age=0")
            .build()
    }

    @Provides
    fun provideOkHttpCache(
        @ApplicationContext context: Context
    ): Cache =
        Cache(
            directory = File(context.cacheDir, "http_cache"),
            maxSize = 10L * 1024L * 1024L
        )

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: ErrorInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(errorInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

}