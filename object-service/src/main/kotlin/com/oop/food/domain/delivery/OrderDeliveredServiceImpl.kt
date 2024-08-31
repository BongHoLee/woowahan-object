package com.oop.food.domain.delivery

import com.oop.food.domain.order.OrderDeliveredService
import com.oop.food.domain.order.OrderPayedService
import com.oop.food.domain.order.OrderRepository
import com.oop.food.domain.shop.ShopRepository
import org.springframework.stereotype.Component

@Component
class OrderDeliveredServiceImpl(
    private val orderRepository: OrderRepository,
    private val shopRepository: ShopRepository,
    private val deliveryRepository: DeliveryRepository
) : OrderPayedService, OrderDeliveredService {

    override fun payOrder(orderId: Long) {
        val order = orderRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }
            .apply { payed() }

        deliveryRepository.save(Delivery.started(order.id()!!))
    }

    override fun deliverOrder(orderId: Long) {
        val order = orderRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }

        val shop = shopRepository.findById(order.shopId())
            .orElseThrow { IllegalArgumentException("Shop not found") }

        val delivery = deliveryRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Delivery not found") }


        order.delivered()
        shop.billCommissionFee(order.calculateTotalPrice())
        delivery.complete()
    }
}