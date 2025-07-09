package com.dd3ok.fomodoro.controller

import com.dd3ok.fomodoro.batch.FearGreedIndexBatchJob
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/batch")
class BatchController(
    private val fearGreedIndexBatchJob: FearGreedIndexBatchJob
) {

    @PostMapping("/run/fear-greed")
    fun runFearGreedIndexJob(): ResponseEntity<String> {
        try {
            fearGreedIndexBatchJob.runScrapeJob()
            return ResponseEntity.ok("Fear and Greed Index batch job has been successfully triggered.")
        } catch (e: Exception) {
            return ResponseEntity.internalServerError()
                .body("Failed to trigger Fear and Greed Index batch job: ${e.message}")
        }
    }
}