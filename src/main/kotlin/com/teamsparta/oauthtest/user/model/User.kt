package com.teamsparta.oauthtest.user.model

import com.teamsparta.oauthtest.user.dto.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column
    val nickname: String,

    @Column
    val providerId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun toResponse(): UserResponse {
        return UserResponse(
            id = this.id ?: throw IllegalStateException("User id cannot be null"),
            nickname = this.nickname,
        )
    }
}