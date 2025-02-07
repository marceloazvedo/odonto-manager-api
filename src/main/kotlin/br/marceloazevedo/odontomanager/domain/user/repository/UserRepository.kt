package br.marceloazevedo.odontomanager.domain.user.repository

import br.marceloazevedo.odontomanager.domain.user.model.UserEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<UserEntity, Long> {
    @Query("select * from app_user where email = $1 limit 1")
    fun findByEmail(email: String): Mono<UserEntity>
}
