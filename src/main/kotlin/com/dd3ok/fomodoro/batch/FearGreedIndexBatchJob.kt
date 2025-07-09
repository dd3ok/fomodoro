package com.dd3ok.fomodoro.batch

import com.dd3ok.fomodoro.client.ExternalApiException
import com.dd3ok.fomodoro.client.FearGreedIndexClient
import com.dd3ok.fomodoro.service.FearGreedIndexService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FearGreedIndexBatchJob(
    private val fearGreedIndexClient: FearGreedIndexClient,
    private val service: FearGreedIndexService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 매시간 정각에 Fear and Greed Index 데이터를 수집합니다.
     * 예: 01:00, 02:00, 03:00 ...
     */
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    fun runScrapeJob() {
        logger.info("Starting Fear and Greed Index fetch job.")
        try {
            val fetchedData = fearGreedIndexClient.fetchData()
            service.save(fetchedData)
            logger.info("Successfully saved Fear and Greed Index for date: ${fetchedData.date}")
        } catch (e: ExternalApiException) {
            logger.error("Error during fetching Fear and Greed Index data: ${e.message}", e)
        } catch (e: IllegalStateException) {
            logger.warn(e.message)
        } catch (e: Exception) {
            logger.error("An unexpected error occurred during the fetch job.", e)
        }
    }
}