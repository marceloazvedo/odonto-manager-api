package br.marceloazevedo.odontomanager.infrastructure.config.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails

class JwtTokenAuthentication(
    val token: String? = null,
    private val principal: UserDetails,
) : AbstractAuthenticationToken(principal.authorities) {
    fun withAuthenticated(isAuthenticated: Boolean): Authentication {
        val cloned = JwtTokenAuthentication(token, principal)
        cloned.isAuthenticated = isAuthenticated
        return cloned
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return principal
    }
}
