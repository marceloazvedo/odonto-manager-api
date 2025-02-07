package br.marceloazevedo.odontomanager.infrastructure.filter

import br.marceloazevedo.odontomanager.domain.user.service.IsUserJwtValidService
import br.marceloazevedo.odontomanager.infrastructure.config.security.JwtTokenAuthentication
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter(
    private val isUserJwtValidService: IsUserJwtValidService,
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.just(authentication)
            .cast(JwtTokenAuthentication::class.java)
            .filter { jwtToken -> isUserJwtValidService.execute(jwtToken.token!!) }
            .map { jwtToken -> jwtToken.withAuthenticated(true) }
            .switchIfEmpty(Mono.error(RuntimeException("JwtException, invalid token melhorar")))
    }
}
