package com.dd3ok.fomodoro.client

import com.fasterxml.jackson.annotation.JsonProperty

data class FearAndGreedApiResponse(
    @JsonProperty("fear_and_greed")
    val fearAndGreed: FearGreedMainData?,

    @JsonProperty("market_momentum_sp500")
    val marketMomentum: IndicatorData?,

    @JsonProperty("stock_price_strength")
    val stockPriceStrength: IndicatorData?,

    @JsonProperty("stock_price_breadth")
    val stockPriceBreadth: IndicatorData?,

    @JsonProperty("put_and_call_options")
    val putAndCallOptions: IndicatorData?,

    @JsonProperty("market_volatility_vix")
    val marketVolatility: IndicatorData?,

    @JsonProperty("safe_haven_demand")
    val safeHavenDemand: IndicatorData?,

    @JsonProperty("junk_bond_demand")
    val junkBondDemand: IndicatorData?
)

data class FearGreedMainData(
    val score: Double?,
    val rating: String?,
    val timestamp: String?,
    @JsonProperty("previous_close")
    val previousClose: Double?,
    @JsonProperty("previous_1_week")
    val previous1Week: Double?,
    @JsonProperty("previous_1_month")
    val previous1Month: Double?,
    @JsonProperty("previous_1_year")
    val previous1Year: Double?
)

data class IndicatorData(
    val score: Double?,
    val rating: String?
)