package cucerdariancatalin.api.call

sealed class ResultApi {
    data class Success(val data: Any?): ResultApi()
    data class Error(val message: String? = null, val statusCode: Int? = null): ResultApi()
    data class Empty(val error: String? = "Vazio"): ResultApi()
}