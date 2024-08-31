package com.oop.food.domain.order

import com.oop.food.domain.shop.Menu
import com.oop.food.domain.shop.MenuRepository
import com.oop.food.domain.shop.Shop
import com.oop.food.domain.shop.ShopRepository
import org.springframework.stereotype.Component

@Component
class OrderValidator(
    private val shopRepository: ShopRepository,
    private val menuRepository: MenuRepository
) {

    fun validate(order: Order) {
        validate(order, associatedShop(order), associatedMenus(order))
    }

    private fun validate(order: Order, shop: Shop, menus: Map<Long, Menu>) {
        if (!shop.isOpen()) {
            throw IllegalArgumentException("가게가 영업중이 아닙니다.")
        }

        if (order.orderLineItems().isEmpty()) {
            throw IllegalArgumentException("주문 항목이 비어있습니다.")
        }

        if (!shop.isValidOrderAmount(order.calculateTotalPrice())) {
            throw IllegalArgumentException("주문 금액이 최소 주문 금액보다 적습니다. ${shop.minOrderAmount()}")
        }

        for (orderLineItem in order.orderLineItems()) {
            orderLineItem.validate(menus[orderLineItem.menuId()]!!)
        }
    }

    private fun associatedShop(order: Order): Shop {
        return shopRepository.findById(order.shopId())
            .orElseThrow { IllegalArgumentException("가게를 찾을 수 없습니다.") }
    }

    private fun associatedMenus(order: Order): Map<Long, Menu> {
        return menuRepository.findAllById(order.menuIds())
            .associateBy { it.id()!! }
    }

    private fun OrderLineItem.validate(menu: Menu) {
        if (menu.name() != this.name()) {
            throw IllegalArgumentException("기본 상품이 변경됐습니다.")
        }

        for(group in groups()) {
            group.validate(menu)
        }
    }

    private fun OrderOptionGroup.validate(menu: Menu) {
        for (spec in menu.optionGroupSpecs()) {
            if (spec.isSatisfiedBy(convertToOptionGroup())) {
                return
            }
        }

        throw IllegalArgumentException("메뉴가 변경되었습니다.")
    }
}