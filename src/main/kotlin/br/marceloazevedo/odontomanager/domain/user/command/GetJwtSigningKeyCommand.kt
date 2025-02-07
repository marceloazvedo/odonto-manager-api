package br.marceloazevedo.odontomanager.domain.user.command

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import javax.crypto.SecretKey

@Service
class GetJwtSigningKeyCommand {
    companion object {
        const val SECRET_KEY: String = "AS9DADSADS0ADS0KADAAD0ADAS0DJKAads9da9jddj9asjda9djasj9adsads9jadsd9ajsh8dsa0h80asd"
    }

    fun execute(): SecretKey {
        val bytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(bytes)
    }
}
