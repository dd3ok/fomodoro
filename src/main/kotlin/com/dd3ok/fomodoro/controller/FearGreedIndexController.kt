package com.dd3ok.fomodoro.controller

import com.dd3ok.fomodoro.controller.response.FearGreedIndexDto
import com.dd3ok.fomodoro.service.FearGreedIndexService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/fear-greed")
class FearGreedIndexController(
    private val fearGreedIndexService: FearGreedIndexService
) {

    @GetMapping("/latest")
    fun getLatestFearGreedIndex(): ResponseEntity<FearGreedIndexDto> {
        return fearGreedIndexService.getLatestFearGreedIndex()
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getFearGreedIndexByDate(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<FearGreedIndexDto> {
        return fearGreedIndexService.getFearGreedIndexByDate(date)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}