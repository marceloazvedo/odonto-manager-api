package br.marceloazevedo.odontomanager.infrastructure.config.security

import br.marceloazevedo.odontomanager.domain.user.service.CreateUserDetailsService
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter(
    private val createUserDetailsService: CreateUserDetailsService,
) : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
        Mono.justOrEmpty(exchange.request.cookies["jwt-auth"]?.firstOrNull())
            .map { it.value }
            .flatMap { token ->
                createUserDetailsService.execute(token).map {
                    JwtTokenAuthentication(token, it)
                }
            }
}
