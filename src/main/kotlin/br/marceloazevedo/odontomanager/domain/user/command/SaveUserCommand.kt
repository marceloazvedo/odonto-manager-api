package br.marceloazevedo.odontomanager.domain.user.command

import br.marceloazevedo.odontomanager.api.user.exchange.request.CreateUserRequest
import br.marceloazevedo.odontomanager.domain.user.model.UserEntity
import br.marceloazevedo.odontomanager.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SaveUserCommand(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun execute(createUserRequest: CreateUserRequest): Mono<UserEntity> {
        val passwordEncoded = passwordEncoder.encode(createUserRequest.password)
        val userEntity =
            UserEntity(
                email = createUserRequest.email,
                password = passwordEncoded,
            )
        return userRepository.save(userEntity)
    }
}
