package com.oop.food.domain.order

import com.oop.food.domain.generic.money.Money
import com.oop.food.domain.shop.Option
import com.oop.food.infra.generic.money.MoneyConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable

@Embeddable
class OrderOption(
    @Column(name="NAME")
    val name: String,

    @Column(name="PRICE")
    @Convert(converter = MoneyConverter::class)
    val price: Money
) {
    fun convertToOption(): Option =
        Option(name, price)
}