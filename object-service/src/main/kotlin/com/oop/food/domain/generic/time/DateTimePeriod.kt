package com.oop.food.domain.generic.time

import java.time.LocalDateTime

class DateTimePeriod(
    private val from: LocalDateTime,
    private val to: LocalDateTime
) {
    companion object {
        fun between(from: LocalDateTime, to: LocalDateTime): DateTimePeriod {
            return DateTimePeriod(from, to)
        }
    }

    fun contains(datetime: LocalDateTime): Boolean {
        return (datetime.isAfter(from) || datetime == from) && (datetime.isBefore(to) || datetime == to)
    }
}