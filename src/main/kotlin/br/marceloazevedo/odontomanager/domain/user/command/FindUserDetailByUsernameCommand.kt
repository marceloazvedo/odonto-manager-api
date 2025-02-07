package br.marceloazevedo.odontomanager.domain.user.command

import br.marceloazevedo.odontomanager.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FindUserDetailByUsernameCommand(
    private val userRepository: UserRepository,
) {
    fun execute(email: String?): Mono<UserDetails> {
        return userRepository.findByEmail(email!!)
            .map {
                User.builder()
                    .username(it.email)
                    .password(it.password)
                    .authorities(listOf())
                    .build()
            }
    }
}
