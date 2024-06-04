package com.teamsparta.oauthtest.oauth

import com.teamsparta.oauthtest.oauth.dto.KaKaoToken
import com.teamsparta.oauthtest.oauth.dto.KakaoUserResponse
import com.teamsparta.oauthtest.user.UserService
import com.teamsparta.oauthtest.user.dto.UserResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RequestMapping("/oauth2/kakao")
@Controller
class KaKaoOAuthController(
    @Value("\${oauth2.kakao.auth-url}")
    private val authUrl: String,

    @Value("\${oauth2.kakao.api-url}")
    private val apiUrl: String,

    @Value("\${oauth2.kakao.client-id}")
    private val clientId: String,

    @Value("\${oauth2.kakao.redirect-uri}")
    private val redirectUri: String,

    private val userService: UserService,
    service: UserService
) {

    @GetMapping("/login")
    fun requestKakaoToken(): String {
        val requestUrl =
            "${authUrl}/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&redirect_uri=${redirectUri}&response_type=code"
        return "redirect:$requestUrl"
    }

    @GetMapping("/redirect")
    @ResponseBody
    fun getKaKaoToken(@RequestParam(name = "code") accessToken: String): ResponseEntity<String> {
        val url = "$authUrl/token"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val params = mapOf(
            "client_id" to clientId,
            "redirect_uri" to redirectUri,
            "grant_type" to "authorization_code",
            "code" to accessToken
        )

        val request = HttpEntity(params, headers)
        val kaKaoToken = RestTemplate().postForObject(
            url,
            LinkedMultiValueMap<String, String>().apply { setAll(params) },
            KaKaoToken::class.java
        )

        return ResponseEntity
            .ok()
            .body(kaKaoToken?.accessToken)
    }

    @GetMapping("/user")
    @ResponseBody
    fun getUser(@RequestHeader requestHeader: HttpHeaders): ResponseEntity<UserResponse> {
        val accessToken = requestHeader.getOrEmpty("Authorization")[0]?.replace("Bearer ", "")?.trim() ?: ""

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers["Authorization"] = "Bearer $accessToken"

        val kakaoUserResponse = RestTemplate().exchange(
            apiUrl,
            HttpMethod.GET,
            HttpEntity<String>(headers),
            KakaoUserResponse::class.java
        )

        val kakaoUser = kakaoUserResponse.body
        val providerId = kakaoUser?.id ?: throw IllegalStateException("User not found")

        val foundUser = userService.findByProviderId(providerId)

        return if (foundUser != null) {
            ResponseEntity.status(HttpStatus.OK).body(foundUser)
        } else {
            ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(kakaoUser))
        }
    }
}