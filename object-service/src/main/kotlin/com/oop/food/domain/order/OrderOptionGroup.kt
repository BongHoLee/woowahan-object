package com.oop.food.domain.order

import com.oop.food.domain.generic.money.Money
import com.oop.food.domain.shop.OptionGroup
import jakarta.persistence.*

@Entity
@Table(name="ORDER_OPTION_GROUPS")
class OrderOptionGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_OPTION_GROUP_ID")
    private var id: Long? = null,

    @Column(name="NAME")
    private var name: String,


    @ElementCollection
    @CollectionTable(
        name="ORDER_OPTIONS",
        joinColumns = [JoinColumn(name="ORDER_OPTION_GROUP_ID")]
    )
    private var orderOptions: MutableList<OrderOption> = mutableListOf()

) {

    fun calculatePrice(): Money {
        return Money.sum(orderOptions, OrderOption::price)
    }

    fun convertToOptionGroup(): OptionGroup =
        OptionGroup(
            name = name,
            options = orderOptions.map { it.convertToOption() }
        )
}