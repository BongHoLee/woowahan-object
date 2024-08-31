package com.oop.food.domain.shop

import com.oop.food.domain.generic.money.Money
import com.oop.food.infra.generic.money.MoneyConverter
import jakarta.persistence.*
import java.util.Objects

@Entity
@Table(name="OPTION_SPECS")
class OptionSpecification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OPTION_SPEC_ID")
    private var id: Long,

    @Column(name="NAME")
    private var name: String,

    @Column(name="PRICE")
    @Convert(converter = MoneyConverter::class)
    private var price: Money
) {

    fun isSatisfiedBy(option: Option): Boolean {
        return option.name == name && option.price == price
    }
}