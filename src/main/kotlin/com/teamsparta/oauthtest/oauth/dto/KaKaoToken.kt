package com.teamsparta.oauthtest.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KaKaoToken(
    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("expires_in")
    val expiresIn: Int,

    @JsonProperty("refresh_token")
    val refreshToken: String,

    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int,
)