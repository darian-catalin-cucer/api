package cucerdariancatalin.api.error

sealed class ErrorEntity(val message: String, val statusCode: String = "") {
    object Network : ErrorEntity(message = "Error connecting to the server.")
    object NotFound : ErrorEntity(message = "Not found.", statusCode = "404")
    object AccessDenied : ErrorEntity(message = "Access denied.", statusCode = "401")
    object ServiceUnavailable : ErrorEntity(message = "Invalid service.", statusCode = "503")
    object Unknown : ErrorEntity("Unknown error.")
}