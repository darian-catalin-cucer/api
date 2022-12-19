package cucerdariancatalin.api.base

import cucerdariancatalin.api.utils.ext.asyncCall
import kotlinx.coroutines.CoroutineScope

abstract class SimpleUseCase<Type, in Params>  where Type : Any?  {

    abstract suspend fun run(params: Params): Type

    operator fun invoke(scope: CoroutineScope, params: Params) = scope.asyncCall {
        run(params)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun invoke(scope: CoroutineScope) = scope.asyncCall {
        run(None() as Params)
    }

    class None
}