package com.teamsparta.oauthtest.oauth.dto

import com.fasterxml.jackson.databind.ObjectMapper

data class KakaoUserResponse(
    val id: Long,
    val nickname: String
) {
    companion object {
        fun from(json: String): KakaoUserResponse {
            val jsonNode = ObjectMapper().readTree(json)
            return KakaoUserResponse(
                id = jsonNode.get("id").asLong(),
                nickname = jsonNode.get("kakao_account").get("profile").get("nickname").asText()
            )
        }
    }
}