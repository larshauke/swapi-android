package de.larshauke.swapi_android.networking.errors

import java.io.IOException

sealed class SwapiException(override val message: String): IOException(message) {

    data object InternalError : SwapiException("The force is out of balance (internal server error).") {
        private fun readResolve(): Any = InternalError
    }

    data class UnknownServerError(val error: String?) :
        SwapiException("Unknown things are disturbing the force ($error)")

    data object NetworkError : SwapiException("You are not within the force (network error)") {
        private fun readResolve(): Any = NetworkError
    }

    data object TimeoutError : SwapiException("You could not reach the force (timeout error)") {
        private fun readResolve(): Any = TimeoutError
    }

}

