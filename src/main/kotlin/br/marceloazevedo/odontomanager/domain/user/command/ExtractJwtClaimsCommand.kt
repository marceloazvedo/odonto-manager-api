package br.marceloazevedo.odontomanager.domain.user.command

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.crypto.SecretKey

@Service
class ExtractJwtClaimsCommand {
    fun execute(
        jwt: String,
        signingKey: SecretKey,
    ): Mono<Claims> {
        return try {
            Mono.just(
                Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .payload,
            )
        } catch (e: JwtException) {
            Mono.error(e)
        }
    }
}
