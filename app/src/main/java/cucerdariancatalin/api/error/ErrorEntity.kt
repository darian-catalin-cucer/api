package cucerdariancatalin.api.error

sealed class ErrorEntity(val message: String, val statusCode: String = "") {
    object Network : ErrorEntity(message = "Erro ao se conectar com o servidor.")
    object NotFound : ErrorEntity(message = "Não encontrado.", statusCode = "404")
    object AccessDenied : ErrorEntity(message = "Acesso negado.", statusCode = "401")
    object ServiceUnavailable : ErrorEntity(message = "Serviço inválido.", statusCode = "503")
    object Unknown : ErrorEntity("Erro desconhecido.")
}