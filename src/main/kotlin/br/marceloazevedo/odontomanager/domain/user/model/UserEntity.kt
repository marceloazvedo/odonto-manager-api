package br.marceloazevedo.odontomanager.domain.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("APP_USER")
data class UserEntity(
    @Id
    val id: Long? = null,
    val email: String? = null,
    val password: String? = null,
)
