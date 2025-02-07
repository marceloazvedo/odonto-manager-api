package br.marceloazevedo.odontomanager.domain.user.command

import io.jsonwebtoken.Claims
import org.springframework.stereotype.Service

@Service
class ExtractJwtRolesCommand {
    @Suppress("UNCHECKED_CAST")
    fun execute(jwtClaims: Claims): List<String> {
        return jwtClaims["roles"] as List<String>
    }
}
