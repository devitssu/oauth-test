package com.teamsparta.oauthtest.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserResponse(
    val id: Long,

    @JsonProperty("properties")
    val profile: KakaoUserProfileResponse
)