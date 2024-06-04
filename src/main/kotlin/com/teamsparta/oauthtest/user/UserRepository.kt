package com.teamsparta.oauthtest.user

import com.teamsparta.oauthtest.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByProviderId(providerId: Long): Boolean
    fun findByProviderId(providerId: Long): User?
}
