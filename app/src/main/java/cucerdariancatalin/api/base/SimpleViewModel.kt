package cucerdariancatalin.api.base

import androidx.lifecycle.ViewModel
import cucerdariancatalin.api.call.ResultApi

abstract class SimpleViewModel<Type> : ViewModel() {

    private var onSuccess: (Type) -> Unit = {}
    private var onError: (() -> Unit?) = {}

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> T.safeCall(
        call: suspend () -> ResultApi
    ) {
        when(val response = call.invoke()) {
            is ResultApi.Success -> { onSuccess(response.data as Type) }
            is ResultApi.Error -> { onError.let { onError.invoke() } }
        }
    }

    fun <T> T.onSuccess(action: (value: Type) -> Unit) {
        onSuccess = action
    }

    fun <T> T.onError(action: (() -> Unit?)) {
        onError = action
    }
}