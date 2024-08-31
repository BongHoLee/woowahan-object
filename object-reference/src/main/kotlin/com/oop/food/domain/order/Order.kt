package com.oop.food.domain.order

import com.oop.food.domain.generic.money.Money
import com.oop.food.domain.shop.Shop
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ORDERS")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private var id: Long? = null,

    @Column(name = "USER_ID")
    private var userId: Long,

    @ManyToOne
    @JoinColumn(name="SHOP_ID")
    private var shop: Shop,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name="ORDER_ID")
    private var orderLineItems: MutableList<OrderLineItem> = mutableListOf(),

    @Column(name="ORDERED_TIME")
    private var orderTime: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private var orderStatus: OrderStatus? = null

) {
    enum class OrderStatus { ORDERED, PAYED, DELIVERED }

    fun place() {
        validate()
        ordered()
    }

    private fun validate() {
        if (orderLineItems.isEmpty()) {
            throw IllegalArgumentException("주문 항목이 비어있습니다.")
        }

        if (!shop.isOpen()) {
            throw IllegalArgumentException("가게가 영업중이 아닙니다.")
        }

        if (!shop.isValidOrderAmount(calculateTotalPrice())) {
            throw IllegalArgumentException("주문 금액이 최소 주문 금액보다 적습니다. ${shop.minOrderAmount()}")
        }

        for (orderLineItem in orderLineItems) {
            orderLineItem.validate()
        }
    }

    private fun ordered() {
        this.orderStatus = OrderStatus.ORDERED
    }

    fun payed() {
        this.orderStatus = OrderStatus.PAYED
    }

    fun delivered() {
        this.orderStatus = OrderStatus.DELIVERED
        this.shop.billCommissionFee(calculateTotalPrice())
    }

    private fun calculateTotalPrice(): Money {
        return Money.sum(orderLineItems, OrderLineItem::calculatePrice)
    }
}
