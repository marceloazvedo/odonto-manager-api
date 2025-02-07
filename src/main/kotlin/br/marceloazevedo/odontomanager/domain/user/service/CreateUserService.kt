package br.marceloazevedo.odontomanager.domain.user.service

import br.marceloazevedo.odontomanager.api.user.exchange.request.CreateUserRequest
import br.marceloazevedo.odontomanager.api.user.exchange.response.UserCreatedResponse
import br.marceloazevedo.odontomanager.domain.user.command.SaveUserCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateUserService(
    private val saveUserCommand: SaveUserCommand,
) {
    fun execute(createUserRequest: CreateUserRequest): Mono<ResponseEntity<UserCreatedResponse>> =
        saveUserCommand.execute(createUserRequest).map {
            ResponseEntity.status(HttpStatus.CREATED).body(UserCreatedResponse(it.email!!))
        }
}
