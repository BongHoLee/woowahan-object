package com.oop.food.infra.generic.money

import com.oop.food.domain.generic.money.Money
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class MoneyConverter : AttributeConverter<Money, Long> {
    override fun convertToDatabaseColumn(money: Money): Long = money.amount.toLong()
    override fun convertToEntityAttribute(amountFromDB: Long): Money = Money.wons(amountFromDB)
}
