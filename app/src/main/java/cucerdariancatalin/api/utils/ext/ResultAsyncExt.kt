package cucerdariancatalin.api.utils.ext

import cucerdariancatalin.api.call.ResultAsync
import cucerdariancatalin.api.error.ErrorEntity
import cucerdariancatalin.api.utils.AsyncStatus
import kotlinx.coroutines.CoroutineScope

fun <T> ResultAsync<T>.onFailure(action: (exception: ErrorEntity) -> Unit): ResultAsync<T> {
    this.onError = action
    return this
}

fun <T> ResultAsync<T>.onSuccess(action: (value: T) -> Unit): ResultAsync<T> {
    this.onSuccess = action
    return this
}

fun <T> ResultAsync<T>.onStatusChange(action: (AsyncStatus) -> Unit): ResultAsync<T> {
    this.onStatusChange = action
    return this
}

fun <T> CoroutineScope.asyncCall(action: suspend () -> T): ResultAsync<T> {
    return ResultAsync.with(this, action)
}