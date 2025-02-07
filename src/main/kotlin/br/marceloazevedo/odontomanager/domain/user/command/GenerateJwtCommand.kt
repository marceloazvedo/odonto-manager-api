package br.marceloazevedo.odontomanager.domain.user.command

import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class GenerateJwtCommand {
    fun execute(
        userDetails: UserDetails,
        signingKey: SecretKey,
    ): String {
        val currentTimeMillis = System.currentTimeMillis()
        return Jwts.builder()
            .claims(emptyMap<String, String>())
            .subject(userDetails.username)
            .claim("roles", userDetails.authorities.map { it.authority.removePrefix("ROLE_") })
            .issuedAt(Date(currentTimeMillis))
            .expiration(Date(currentTimeMillis + 3600 * 1000))
            .signWith(signingKey, Jwts.SIG.HS256)
            .compact()
    }
}
