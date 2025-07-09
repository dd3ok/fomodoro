package com.dd3ok.fomodoro.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/healthcheck")
class HealthcheckController {

    @GetMapping
    fun healthcheck(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}
