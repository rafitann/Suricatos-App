package com.app.suricatos.utils

enum class Status {
    SUCCESS,
    CACHED,
    ERROR,
    LOADING,
    IDLE,
    NETWORK_ERROR,
}

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val error: Exception? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data)
        fun <T> cached(data: T): Resource<T> = Resource(status = Status.CACHED, data = data)
        fun <T> error(
            message: String? = null,
            errorResponse: java.lang.Exception? = null
        ): Resource<T> =
            Resource(status = Status.ERROR, message = message, error = errorResponse)

        fun <T> networkError(): Resource<T> = Resource(status = Status.NETWORK_ERROR)
        fun <T> loading(): Resource<T> = Resource(status = Status.LOADING)
        fun <T> idle(): Resource<T> = Resource(status = Status.IDLE)
    }
}