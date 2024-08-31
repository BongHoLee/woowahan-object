package com.oop.food.domain.order

import com.oop.food.domain.generic.money.Money
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

    // shop에 대한 객체참조를 끊고 id만 참조하도록 변경
    @Column(name = "SHOP_ID")
    private var shopId: Long,

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

    fun place(orderValidator: OrderValidator) {
        orderValidator.validate(this)
        ordered()
    }

    private fun ordered() {
        this.orderStatus = OrderStatus.ORDERED
    }

    fun id(): Long? {
        return id
    }

    fun payed() {
        this.orderStatus = OrderStatus.PAYED
    }

    fun delivered() {
        this.orderStatus = OrderStatus.DELIVERED
    }

    fun orderLineItems(): List<OrderLineItem> {
        return orderLineItems.toList()
    }

    fun menuIds(): List<Long> {
        return orderLineItems.map { it.menuId() }
    }

    fun shopId(): Long {
        return shopId
    }

    fun calculateTotalPrice(): Money {
        return Money.sum(orderLineItems, OrderLineItem::calculatePrice)
    }
}
