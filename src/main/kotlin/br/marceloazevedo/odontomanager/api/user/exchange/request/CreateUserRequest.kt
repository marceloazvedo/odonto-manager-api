package br.marceloazevedo.odontomanager.api.user.exchange.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateUserRequest(
    @NotBlank @NotNull
    val email: String? = null,
    @NotBlank @NotNull
    val password: String? = null,
)
