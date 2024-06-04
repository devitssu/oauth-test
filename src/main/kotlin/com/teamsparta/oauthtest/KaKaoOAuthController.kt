package com.teamsparta.oauthtest

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.RestTemplate


@RequestMapping("/oauth2/kakao")
@Controller
class KaKaoOAuthController(
    @Value("\${oauth2.kakao.url}")
    private val url: String,

    @Value("\${oauth2.kakao.client-id}")
    private val clientId: String,

    @Value("\${oauth2.kakao.redirect-uri}")
    private val redirectUri: String,
) {

    @GetMapping("/login")
    fun requestKakaoToken(): String {
        val requestUrl =
            "${url}/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&redirect_uri=${redirectUri}&response_type=code"
        return "redirect:$requestUrl"
    }

    @GetMapping("/redirect")
    @ResponseBody
    fun getKaKaoToken(@RequestParam(name = "code") accessToken: String): ResponseEntity<String> {
        val url = "$url/token"

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
}