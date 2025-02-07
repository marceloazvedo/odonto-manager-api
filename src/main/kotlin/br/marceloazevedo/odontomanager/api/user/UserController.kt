package br.marceloazevedo.odontomanager.api.user

import br.marceloazevedo.odontomanager.api.user.exchange.request.CreateUserRequest
import br.marceloazevedo.odontomanager.api.user.exchange.request.CredentialRequest
import br.marceloazevedo.odontomanager.api.user.exchange.response.UserCreatedResponse
import br.marceloazevedo.odontomanager.domain.user.service.AuthenticateUserService
import br.marceloazevedo.odontomanager.domain.user.service.CreateUserService
import br.marceloazevedo.odontomanager.domain.user.service.ListUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserController(
    private val authenticateUserService: AuthenticateUserService,
    private val createUserService: CreateUserService,
    private val listUserService: ListUserService,
) {
    @PostMapping
    fun create(
        @RequestBody userToCreate: CreateUserRequest? = null,
    ): Mono<ResponseEntity<UserCreatedResponse>> = createUserService.execute(userToCreate!!)

    @GetMapping
    fun list(): Mono<ResponseEntity<UserCreatedResponse>> = listUserService.execute()

    @PostMapping
    @RequestMapping("/authenticate")
    fun authenticate(
        @RequestBody credentials: CredentialRequest? = null,
    ): Mono<ResponseEntity<Any>> = authenticateUserService.execute(credentials!!)
}
