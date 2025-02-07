package br.marceloazevedo.odontomanager.domain.user.service

import br.marceloazevedo.odontomanager.domain.user.command.GetJwtSigningKeyCommand
import br.marceloazevedo.odontomanager.domain.user.command.IsTokenExpiredCommand
import org.springframework.stereotype.Service

@Service
class IsUserJwtValidService(
    private val isTokenExpiredCommand: IsTokenExpiredCommand,
    getJwtSigningKeyCommand: GetJwtSigningKeyCommand,
) {
    private val signingKey = getJwtSigningKeyCommand.execute()

    fun execute(jwt: String) = isTokenExpiredCommand.execute(jwt, signingKey)
}
