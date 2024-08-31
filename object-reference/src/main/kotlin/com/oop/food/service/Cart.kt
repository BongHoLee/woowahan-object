package com.oop.food.service

import com.oop.food.domain.generic.money.Money

data class Cart(
    val shopId: Long,
    val userId: Long,
    val cartLineItems: MutableList<CartLineItem> = mutableListOf()
) {

    data class CartLineItem(
        val menuId: Long,
        val name: String,
        val count: Int,
        val groups: MutableList<CartOptionGroup> = mutableListOf()
    )

    data class CartOptionGroup(
        val name: String,
        val options: MutableList<CartOption> = mutableListOf()
    )

    data class CartOption(
        val name: String,
        val price: Money
    )
}