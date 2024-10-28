package de.larshauke.swapi_android.networking.interceptor

import de.larshauke.swapi_android.networking.errors.SwapiException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorInterceptor
@Inject
constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        return if (response.isSuccessful || response.code == HttpURLConnection.HTTP_NOT_MODIFIED)
            response
        else
            when (response.code) {
                HttpURLConnection.HTTP_BAD_GATEWAY,
                HttpURLConnection.HTTP_UNAVAILABLE,
                -> throw SwapiException.NetworkError

                HttpURLConnection.HTTP_GATEWAY_TIMEOUT,
                HttpURLConnection.HTTP_CLIENT_TIMEOUT,
                -> throw SwapiException.TimeoutError

                HttpURLConnection.HTTP_INTERNAL_ERROR -> throw SwapiException.InternalError

                else ->
                    throw SwapiException.UnknownServerError("Unknown error")
            }
    }
}