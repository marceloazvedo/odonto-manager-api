package br.marceloazevedo.odontomanager.domain.user.command

import org.springframework.boot.web.server.Cookie
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class GenerateJwtSecureCookieCommand {
    fun execute(jwt: String): ResponseCookie =
        ResponseCookie.from("jwt-auth", jwt)
            .httpOnly(true)
            .secure(true)
            .sameSite(Cookie.SameSite.STRICT.attributeValue())
            .path("/")
            .maxAge(Duration.ofDays(7))
            .build()
}
