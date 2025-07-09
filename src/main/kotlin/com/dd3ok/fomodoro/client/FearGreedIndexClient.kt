package com.dd3ok.fomodoro.client

import com.dd3ok.fomodoro.domain.FearGreedIndex
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.roundToInt

@Component
class FearGreedIndexClient(
    @Value("\${crawling.fear-and-greed.url}") private val apiUrl: String,
    private val restTemplate: RestTemplate
) {
    fun fetchData(): FearGreedIndex {
        try {
            val headers = HttpHeaders().apply {
                accept = listOf(MediaType.APPLICATION_JSON)
                add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                add("Accept", "application/json, text/plain, */*")
                add("Accept-Language", "en-US,en;q=0.9")
                add("Referer", "https://edition.cnn.com/")
            }

            val entity = HttpEntity<String>(headers)
            val responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                FearAndGreedApiResponse::class.java
            )

            val response = responseEntity.body
                ?: throw ExternalApiException("API response body was null.")

            val mainFearGreed = response.fearAndGreed
                ?: throw ExternalApiException("Main Fear & Greed data is missing.")

            val timestamp = mainFearGreed.timestamp ?: throw ExternalApiException("Timestamp is missing.")
            val zonedDateTime = ZonedDateTime.parse(timestamp)
            val startOfDayUtc = zonedDateTime.toLocalDate().atStartOfDay(ZoneId.of("UTC")).toInstant()


            return FearGreedIndex(
                date = startOfDayUtc,
                indexValue = mainFearGreed.score?.roundToInt() ?: 0,
                indexCategory = mainFearGreed.rating?.capitalize() ?: "Unknown",
                previousClose = mainFearGreed.previousClose,
                previous1Week = mainFearGreed.previous1Week,
                previous1Month = mainFearGreed.previous1Month,
                previous1Year = mainFearGreed.previous1Year,
                indicators = FearGreedIndex.Indicators(
                    momentum = mapToIndicatorDetail(response.marketMomentum),
                    strength = mapToIndicatorDetail(response.stockPriceStrength),
                    breadth = mapToIndicatorDetail(response.stockPriceBreadth),
                    putCallRatio = mapToIndicatorDetail(response.putAndCallOptions),
                    volatility = mapToIndicatorDetail(response.marketVolatility),
                    safeHavenDemand = mapToIndicatorDetail(response.safeHavenDemand),
                    junkBondDemand = mapToIndicatorDetail(response.junkBondDemand)
                )
            )
        } catch (e: RestClientException) {
            throw ExternalApiException("Failed to call Fear and Greed API at $apiUrl", e)
        }
    }

    private fun mapToIndicatorDetail(indicatorData: IndicatorData?): FearGreedIndex.IndicatorDetail {
        return FearGreedIndex.IndicatorDetail(
            value = indicatorData?.score?.roundToInt() ?: 0,
            category = indicatorData?.rating?.capitalize() ?: "Unknown"
        )
    }

    private fun String.capitalize(): String =
        this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

class ExternalApiException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)