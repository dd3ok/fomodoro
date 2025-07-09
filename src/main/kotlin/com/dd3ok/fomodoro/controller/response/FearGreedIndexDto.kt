package com.dd3ok.fomodoro.controller.response

import com.dd3ok.fomodoro.domain.FearGreedIndex
import java.time.Instant

data class FearGreedIndexDto(
    val date: Instant,
    val indexValue: Int,
    val indexCategory: String,
    val previousClose: Double?,
    val previous1Week: Double?,
    val previous1Month: Double?,
    val previous1Year: Double?,
    val indicators: FearGreedIndex.Indicators
) {
    companion object {
        fun from(entity: FearGreedIndex): FearGreedIndexDto {
            return FearGreedIndexDto(
                date = entity.date,
                indexValue = entity.indexValue,
                indexCategory = entity.indexCategory,
                previousClose = entity.previousClose,
                previous1Week = entity.previous1Week,
                previous1Month = entity.previous1Month,
                previous1Year = entity.previous1Year,
                indicators = entity.indicators
            )
        }
    }
}