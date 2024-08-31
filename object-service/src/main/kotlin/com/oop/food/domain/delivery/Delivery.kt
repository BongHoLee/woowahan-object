package com.oop.food.domain.delivery

import com.oop.food.domain.order.Order
import jakarta.persistence.*

@Entity
@Table(name="DELIVERIES")
class Delivery(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DELIVERY_ID")
    private var id: Long? = null,

    @Column(name="ORDER_ID")
    private var orderId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private var deliveryStatus: DeliveryStatus
) {

    enum class DeliveryStatus { DELIVERING, DELIVERED }

    companion object {
        fun started(orderId: Long): Delivery =
            Delivery(
                orderId = orderId,
                deliveryStatus = DeliveryStatus.DELIVERING
            )
    }

    fun complete() {
        deliveryStatus = DeliveryStatus.DELIVERED
    }
}