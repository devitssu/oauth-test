package com.teamsparta.oauthtest.user

import com.teamsparta.oauthtest.oauth.dto.KakaoUserResponse
import com.teamsparta.oauthtest.user.dto.UserResponse
import com.teamsparta.oauthtest.user.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun signUp(user: KakaoUserResponse): UserResponse {
        return userRepository.save(User(providerId = user.id, nickname = user.profile.nickname)).toResponse()
    }

    fun findByProviderId(providerId: Long): UserResponse? {
        return userRepository.findByProviderId(providerId)?.toResponse()
    }
}
