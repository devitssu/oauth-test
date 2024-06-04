package com.teamsparta.oauthtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OauthTestApplication

fun main(args: Array<String>) {
    runApplication<OauthTestApplication>(*args)
}
