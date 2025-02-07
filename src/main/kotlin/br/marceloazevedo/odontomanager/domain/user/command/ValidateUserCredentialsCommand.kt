package br.marceloazevedo.odontomanager.domain.user.command

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ValidateUserCredentialsCommand(
    private val passwordEncoder: PasswordEncoder,
) {
    fun execute(
        password1: String,
        password2: String,
    ) = passwordEncoder.matches(password1, password2)
}
