package br.marceloazevedo.odontomanager.infrastructure.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    fun securityFilterChain(
        http: ServerHttpSecurity,
        authenticationManager: ReactiveAuthenticationManager,
        authenticationConverter: ServerAuthenticationConverter,
    ): SecurityWebFilterChain {
        val authenticationFilter = AuthenticationWebFilter(authenticationManager)
        authenticationFilter.setServerAuthenticationConverter(authenticationConverter)

        http
            .csrf { it.disable() }
            .cors { }
            .authorizeExchange { authorize ->
                authorize
                    .pathMatchers(HttpMethod.POST, "/users/authenticate", "/users").permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHORIZATION)
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()
}
