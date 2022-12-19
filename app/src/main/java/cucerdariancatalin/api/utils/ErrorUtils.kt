package cucerdariancatalin.api.utils

import cucerdariancatalin.api.NetworkService.createClientByService
import cucerdariancatalin.api.error.ApiError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

internal object ErrorUtils {
    fun parserError(response: Response<*>): ApiError? {
        val converter: Converter<ResponseBody, ApiError> =
            createClientByService<Retrofit>().responseBodyConverter(ApiError::class.java, arrayOfNulls<Annotation>(0))

        var error: ApiError? = null

        try {
            response.errorBody()?.let { errorBody ->
                error = converter.convert(errorBody)
            }
        } catch (e: IOException){
            return ApiError
        }

        return error
    }
}