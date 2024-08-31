package com.oop.food.domain.order

import com.oop.food.domain.generic.money.Money
import com.oop.food.domain.shop.OptionGroup
import jakarta.persistence.*


@Entity
@Table(name="ORDER_LINE_ITEMS")
class OrderLineItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_LINE_ITEM_ID")
    private var id: Long? = null,

    // menu에 대한 객체참조를 끊고 id만 참조하도록 변경
    @Column(name="MENU_ID")
    private var menuId: Long,

    @Column(name="FOOD_NAME")
    private var name: String,

    @Column(name="FOOD_COUNT")
    private var count: Int,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name="ORDER_LINE_ITEM_ID")
    private var groups: MutableList<OrderOptionGroup> = mutableListOf()
) {

    fun calculatePrice(): Money {
        return Money.sum(groups, OrderOptionGroup::calculatePrice)
    }

    fun name(): String = name

    fun menuId(): Long = menuId

    fun groups(): List<OrderOptionGroup> = groups
}