package br.marceloazevedo.odontomanager.domain.user.service

import br.marceloazevedo.odontomanager.domain.user.command.ExtractJwtClaimsCommand
import br.marceloazevedo.odontomanager.domain.user.command.ExtractJwtRolesCommand
import br.marceloazevedo.odontomanager.domain.user.command.GetJwtSigningKeyCommand
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.crypto.SecretKey

@Service
class CreateUserDetailsService(
    private val extractJwtClaimsCommand: ExtractJwtClaimsCommand,
    private val extractJwtRolesCommand: ExtractJwtRolesCommand,
    getJwtSigningKeyCommand: GetJwtSigningKeyCommand,
) {
    private var signingKey: SecretKey = getJwtSigningKeyCommand.execute()

    fun execute(jwt: String): Mono<UserDetails> {
        return extractJwtClaimsCommand.execute(jwt, signingKey)
            .flatMap { claims ->
                createAuthorities(jwt)
                    .map { authorities ->
                        User.builder()
                            .username(claims.subject)
                            .authorities(authorities)
                            .password("")
                            .build()
                    }
            }
    }

    private fun createAuthorities(token: String): Mono<List<SimpleGrantedAuthority>> =
        extractJwtClaimsCommand.execute(token, signingKey)
            .map {
                extractJwtRolesCommand.execute(it)
                    .stream()
                    .map { role -> "ROLE_$role" }
                    .map { role: String? -> SimpleGrantedAuthority(role) }
                    .toList()
            }
}
