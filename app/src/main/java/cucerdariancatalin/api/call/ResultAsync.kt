package cucerdariancatalin.api.call

import cucerdariancatalin.api.error.ErrorEntity
import cucerdariancatalin.api.error.ErrorHandler
import cucerdariancatalin.api.utils.AsyncStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ResultAsync<T> private constructor(
    scope: CoroutineScope, val action: suspend () -> T
) : KoinComponent {

    internal var onSuccess: (T) -> Unit = {}
    internal var onError: (e: ErrorEntity) -> Unit = {}
    internal var onStatusChange: (status: AsyncStatus) -> Unit = {}
    private val errorHandler = ErrorHandler()

    companion object {
        fun <T> with(scope: CoroutineScope, action: suspend () -> T): ResultAsync<T> {
            return ResultAsync(scope, action)
        }
    }

    init {
        scope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { onStatusChange(AsyncStatus.RUNNING) }
            runAction(success = {
                withContext(Dispatchers.Main) {
                    onStatusChange(AsyncStatus.DONE)
                    onSuccess(it)
                }
            }, error = {
                withContext(Dispatchers.Main) {
                    onStatusChange(AsyncStatus.ERROR)
                    onError(it)
                }
            })
        }
    }

    private suspend fun runAction(success: suspend (T) -> Unit, error: suspend (ErrorEntity) -> Unit) {
        try {
            val result = action()
            success(result)
        } catch (e: Exception) {
            error(errorHandler.handlerError(e))
        }
    }
}