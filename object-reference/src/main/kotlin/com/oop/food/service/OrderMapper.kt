package com.oop.food.service

import com.oop.food.domain.order.Order
import com.oop.food.domain.order.OrderLineItem
import com.oop.food.domain.order.OrderOption
import com.oop.food.domain.order.OrderOptionGroup
import com.oop.food.domain.shop.MenuRepository
import com.oop.food.domain.shop.ShopRepository
import org.springframework.stereotype.Component

@Component
class OrderMapper(
    private val menuRepository: MenuRepository,
    private val shopRepository: ShopRepository
) {

    fun mapFrom(cart: Cart): Order {
        val shop = shopRepository.findById(cart.shopId)
            .orElseThrow { IllegalArgumentException("Shop not found") }

        return Order(
            userId = cart.userId,
            shop = shop,
            orderLineItems = cart.cartLineItems.map { it.toOrderLineItem() }.toMutableList()
        )
    }

    private fun Cart.CartLineItem.toOrderLineItem(): OrderLineItem {
        val menu = menuRepository.findById(this.menuId)
            .orElseThrow { IllegalArgumentException("Menu not found") }

        return OrderLineItem(
            menu = menu,
            name = this.name,
            count = this.count,
            groups = this.groups.map { it.toOrderOptionGroup() }.toMutableList()
        )
    }

    private fun Cart.CartOptionGroup.toOrderOptionGroup(): OrderOptionGroup {
        return OrderOptionGroup(
            name = this.name,
            orderOptions = this.options.map { it.toOrderOption() }.toMutableList()
        )
    }

    private fun Cart.CartOption.toOrderOption(): OrderOption {
        return OrderOption(
            name = this.name,
            price = this.price
        )
    }
}