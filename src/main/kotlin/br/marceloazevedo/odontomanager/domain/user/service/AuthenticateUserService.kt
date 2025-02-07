package br.marceloazevedo.odontomanager.domain.user.service

import br.marceloazevedo.odontomanager.api.user.exchange.request.CredentialRequest
import br.marceloazevedo.odontomanager.domain.user.command.FindUserDetailByUsernameCommand
import br.marceloazevedo.odontomanager.domain.user.command.GenerateJwtCommand
import br.marceloazevedo.odontomanager.domain.user.command.GenerateJwtSecureCookieCommand
import br.marceloazevedo.odontomanager.domain.user.command.GetJwtSigningKeyCommand
import br.marceloazevedo.odontomanager.domain.user.command.ValidateUserCredentialsCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthenticateUserService(
    private val findUserDetailByUsernameCommand: FindUserDetailByUsernameCommand,
    private val validateUserCredentialsCommand: ValidateUserCredentialsCommand,
    private val generateJwtCommand: GenerateJwtCommand,
    private val getJwtSigningKeyCommand: GetJwtSigningKeyCommand,
    private val generateJwtSecureCookieCommand: GenerateJwtSecureCookieCommand,
) {
    fun execute(credentials: CredentialRequest): Mono<ResponseEntity<Any>> =
        findUserDetailByUsernameCommand.execute(credentials.email)
            .filter { user -> validateUserCredentialsCommand.execute(credentials.password!!, user.password) }
            .map { Pair(it, getJwtSigningKeyCommand.execute()) }
            .map { generateJwtCommand.execute(it.first, it.second) }
            .map { generateJwtSecureCookieCommand.execute(it) }
            .map {
                ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Set-Cookie", it.toString())
                    .build<Any>()
            }.switchIfEmpty(Mono.error(RuntimeException("Deu error my friend")))
}
