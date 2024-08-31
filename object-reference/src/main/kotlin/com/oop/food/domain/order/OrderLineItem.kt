package com.oop.food.domain.order

import com.oop.food.domain.generic.money.Money
import com.oop.food.domain.shop.Menu
import com.oop.food.domain.shop.OptionGroup
import jakarta.persistence.*


@Entity
@Table(name="ORDER_LINE_ITEMS")
class OrderLineItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_LINE_ITEM_ID")
    private var id: Long? = null,

    @ManyToOne
    @JoinColumn(name="MENU_ID")
    private var menu: Menu,

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

    fun validate() {
        // menu <-> orderLineItem의 양방향 의존성을 제거하기 위해 자기 자신이 아니라 OptionGroup으로 변환해서 넘겨준다.
        // 의존성 역전 원칙 적용
        menu.validateOrder(name, convertTopOptionGroups())
    }

    private fun convertTopOptionGroups(): List<OptionGroup> =
        groups.map { it.convertToOptionGroup() }
}