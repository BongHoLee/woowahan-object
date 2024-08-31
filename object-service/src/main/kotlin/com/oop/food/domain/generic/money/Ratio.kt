package com.oop.food.domain.generic.money

data class Ratio(
    val rate: Double
) {
    companion object {
        fun valueOf(rate: Double): Ratio = Ratio(rate)
    }

    fun of(price: Money): Money = price.times(rate)
}