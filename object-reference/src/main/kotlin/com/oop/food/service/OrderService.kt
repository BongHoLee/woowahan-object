package com.oop.food.service

import com.oop.food.domain.delivery.Delivery
import com.oop.food.domain.delivery.DeliveryRepository
import com.oop.food.domain.order.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val deliveryRepository: DeliveryRepository,
    private val orderMapper: OrderMapper
) {

    @Transactional
    fun placeOrder(cart: Cart) {
        val order = orderMapper.mapFrom(cart)
        order.place()
    }

    @Transactional
    fun payOrder(orderId: Long) {
        val order = orderRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }
            .apply { payed() }

        deliveryRepository.save(Delivery.started(order))
    }

    @Transactional
    fun deliverOrder(orderId: Long) {
        orderRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }
            .apply { delivered() }

        deliveryRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Delivery not found") }
            .apply { complete() }
    }
}