package br.marceloazevedo.odontomanager.domain.user.command

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class IsTokenExpiredCommand {
    fun execute(
        jwt: String,
        signingKey: SecretKey,
    ): Boolean = !isTokenExpired(jwt, signingKey)

    private fun isTokenExpired(
        jwt: String,
        signingKey: SecretKey,
    ): Boolean {
        return extractClaim(jwt, signingKey, Claims::getExpiration).before(Date())
    }

    private fun <T> extractClaim(
        jwt: String,
        signingKey: SecretKey,
        claimResolver: (Claims) -> T,
    ): T {
        val claims = extractAllClaims(jwt, signingKey)
        return claimResolver(claims)
    }

    private fun extractAllClaims(
        jwt: String,
        signingKey: SecretKey,
    ): Claims {
        return try {
            Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(jwt)
                .payload
        } catch (e: JwtException) {
            throw RuntimeException(e.message)
        }
    }
}
