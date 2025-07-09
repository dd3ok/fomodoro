package com.dd3ok.fomodoro.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "fear_and_greed_indices")
data class FearGreedIndex(
    @Id
    val id: String? = null,

    @Indexed(unique = true)
    val date: Instant,

    // Fear & Greed 메인 지표
    val indexValue: Int,
    val indexCategory: String,
    val previousClose: Double?,
    val previous1Week: Double?,
    val previous1Month: Double?,
    val previous1Year: Double?,

    // 7가지 세부 지표
    val indicators: Indicators
) {
    // 7가지 세부 지표를 저장하는 클래스
    data class Indicators(
        val momentum: IndicatorDetail,
        val strength: IndicatorDetail,
        val breadth: IndicatorDetail,
        val putCallRatio: IndicatorDetail,
        val volatility: IndicatorDetail,
        val safeHavenDemand: IndicatorDetail,
        val junkBondDemand: IndicatorDetail
    )

    // 각 세부 지표의 포맷
    data class IndicatorDetail(
        val value: Int,
        val category: String
    )
}