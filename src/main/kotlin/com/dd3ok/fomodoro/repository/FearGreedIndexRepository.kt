package com.dd3ok.fomodoro.repository

import com.dd3ok.fomodoro.domain.FearGreedIndex
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface FearGreedIndexRepository : MongoRepository<FearGreedIndex, String> {
    fun findTopByOrderByDateDesc(): FearGreedIndex?
    fun findByDate(date: Instant): FearGreedIndex?
}