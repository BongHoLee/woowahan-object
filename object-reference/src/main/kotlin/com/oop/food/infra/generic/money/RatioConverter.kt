package com.oop.food.infra.generic.money

import com.oop.food.domain.generic.money.Ratio
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class RatioConverter : AttributeConverter<Ratio, Double> {
    override fun convertToDatabaseColumn(ratio: Ratio): Double = ratio.rate
    override fun convertToEntityAttribute(ratioFromDB: Double): Ratio = Ratio.valueOf(ratioFromDB)
}
