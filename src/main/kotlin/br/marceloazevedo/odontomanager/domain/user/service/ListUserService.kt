package br.marceloazevedo.odontomanager.domain.user.service

import br.marceloazevedo.odontomanager.api.user.exchange.response.UserCreatedResponse
import br.marceloazevedo.odontomanager.domain.user.command.ExtractJwtClaimsCommand
import br.marceloazevedo.odontomanager.domain.user.command.FindUserDetailByUsernameCommand
import br.marceloazevedo.odontomanager.domain.user.command.GetJwtSigningKeyCommand
import br.marceloazevedo.odontomanager.infrastructure.config.security.JwtTokenAuthentication
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ListUserService(
    private val extractJwtClaimsCommand: ExtractJwtClaimsCommand,
    private val findUserDetailByUsernameCommand: FindUserDetailByUsernameCommand,
    getJwtSigningKeyCommand: GetJwtSigningKeyCommand,
) {
    private val signingKey = getJwtSigningKeyCommand.execute()

    fun execute(): Mono<ResponseEntity<UserCreatedResponse>> =
        ReactiveSecurityContextHolder.getContext()
            .map { context -> context.authentication }
            .cast(JwtTokenAuthentication::class.java)
            .flatMap { jwt -> extractJwtClaimsCommand.execute(jwt.token!!, signingKey) }
            .flatMap { findUserDetailByUsernameCommand.execute(it.subject) }
            .map { ResponseEntity.ok().body(UserCreatedResponse(it.username)) }
}
