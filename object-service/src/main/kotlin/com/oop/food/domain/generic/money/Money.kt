package com.oop.food.domain.generic.money

import java.math.BigDecimal

data class Money(
    val amount: BigDecimal
) {
    companion object {
        val ZERO = wons(0)

        fun wons(amount: Long): Money = Money(BigDecimal.valueOf(amount))
        fun wons(amount: Double): Money = Money(BigDecimal.valueOf(amount))

        fun <T> sum(bags: Collection<T>, monetary: (T) -> Money): Money {
            return bags.map {  monetary(it) }.fold(ZERO, Money::plus)
        }
    }

    fun plus(amount: Money): Money =  Money(this.amount.add(amount.amount))
    fun minus(amount: Money): Money =  Money(this.amount.subtract(amount.amount))
    fun times(percent: Double): Money = Money(this.amount.multiply(BigDecimal.valueOf(percent)))
    fun divide(divisor: Double): Money = Money(this.amount.divide(BigDecimal.valueOf(divisor)))

    fun isLessThan(other: Money): Boolean = amount < other.amount
    fun isGreaterThanOrEqual(other: Money): Boolean = amount >= other.amount

    fun longValue(): Long = amount.toLong()
    fun doubleValue(): Double = amount.toDouble()

    override fun toString(): String = "$amount 원"
}