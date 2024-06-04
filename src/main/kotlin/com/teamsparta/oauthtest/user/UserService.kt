package com.teamsparta.oauthtest.user

import com.teamsparta.oauthtest.oauth.dto.KakaoUserResponse
import com.teamsparta.oauthtest.user.dto.UserResponse
import com.teamsparta.oauthtest.user.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun signUp(kakaoUser: KakaoUserResponse): UserResponse {
        return userRepository.save(User(providerId = kakaoUser.id, nickname = kakaoUser.nickname)).toResponse()
    }

    fun findByProviderId(providerId: Long): UserResponse? {
        return userRepository.findByProviderId(providerId)?.toResponse()
    }
}
