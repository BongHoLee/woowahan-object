package com.oop.food.domain.order

interface OrderPayedService {
    fun payOrder(orderId: Long)
}