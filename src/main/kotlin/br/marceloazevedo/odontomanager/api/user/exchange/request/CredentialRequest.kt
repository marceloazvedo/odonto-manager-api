package br.marceloazevedo.odontomanager.api.user.exchange.request

data class CredentialRequest(
    val email: String? = null,
    val password: String? = null,
)
