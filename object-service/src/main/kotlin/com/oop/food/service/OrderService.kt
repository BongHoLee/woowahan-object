package com.oop.food.service

import com.oop.food.domain.order.OrderDeliveredService
import com.oop.food.domain.order.OrderPayedService
import com.oop.food.domain.order.OrderRepository
import com.oop.food.domain.order.OrderValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper,
    private val orderValidator: OrderValidator,
    private val orderDeliveredService: OrderDeliveredService,
    private val orderPayedService: OrderPayedService
) {

    @Transactional
    fun placeOrder(cart: Cart) {
        val order = orderMapper.mapFrom(cart)
        order.place(orderValidator)
        orderRepository.save(order)
    }

    @Transactional
    fun payOrder(orderId: Long) {
        orderPayedService.payOrder(orderId)
    }

    @Transactional
    fun deliverOrder(orderId: Long) {
        orderDeliveredService.deliverOrder(orderId)
    }
}