package com.dd3ok.fomodoro.service

import com.dd3ok.fomodoro.controller.response.FearGreedIndexDto
import com.dd3ok.fomodoro.domain.FearGreedIndex
import com.dd3ok.fomodoro.repository.FearGreedIndexRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class FearGreedIndexService(
    private val fearGreedIndexRepository: FearGreedIndexRepository
) {
    @Transactional
    fun save(fearGreedIndex: FearGreedIndex): FearGreedIndex {
        val existingData = fearGreedIndexRepository.findByDate(fearGreedIndex.date)

        if (existingData != null) {
            return fearGreedIndexRepository.save(
                fearGreedIndex.copy(id = existingData.id)
            )
        }

        return fearGreedIndexRepository.save(fearGreedIndex)
    }

    @Transactional(readOnly = true)
    fun getLatestFearGreedIndex(): FearGreedIndexDto? {
        return fearGreedIndexRepository.findTopByOrderByDateDesc()
            ?.let { FearGreedIndexDto.from(it) }
    }

    @Transactional(readOnly = true)
    fun getFearGreedIndexByDate(date: LocalDate): FearGreedIndexDto? {
        val startOfDayUtc = date.atStartOfDay(ZoneId.of("UTC")).toInstant()
        return fearGreedIndexRepository.findByDate(startOfDayUtc)
            ?.let { FearGreedIndexDto.from(it) }
    }
}