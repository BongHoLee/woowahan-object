package com.oop.food.domain.shop

import com.oop.food.domain.generic.money.Money
import com.oop.food.domain.generic.money.Ratio
import com.oop.food.infra.generic.money.MoneyConverter
import com.oop.food.infra.generic.money.RatioConverter
import jakarta.persistence.*

@Entity
@Table(name = "SHOPS")
class Shop(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOP_ID")
    private var id: Long?,

    @Column(name = "NAME")
    private var name: String,

    @Column(name = "OPEN")
    private var open: Boolean,

    @Column(name = "MIN_ORDER_AMOUNT")
    @Convert(converter = MoneyConverter::class)
    private var minOrderAmount: Money,

    @Column(name = "COMMISSION_RATE")
    @Convert(converter = RatioConverter::class)
    private var commissionRate: Ratio,

    @Column(name = "COMMISSION")
    @Convert(converter = MoneyConverter::class)
    private var commission: Money = Money.ZERO,

    // [조회 전용 양뱡향 연관관계]
    /**
     * MenuBoard 생성을 위한 연관관계
     */
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name="SHOP_ID")
    private var menus: MutableList<Menu> = mutableListOf(),

    ) {

    fun open() {
        open = true
    }

    fun close() {
        open = false
    }

    fun isOpen() = open

    fun addMenu(menu: Menu) {
        menus.add(menu)
    }

    fun isValidOrderAmount(orderAmount: Money) = orderAmount.isGreaterThanOrEqual(minOrderAmount)

    fun modifyCommissionRate(commissionRate: Ratio) {
        this.commissionRate = commissionRate
    }

    fun billCommissionFee(price: Money) {
        commission = commission.plus(commissionRate.of(price))
    }

    fun minOrderAmount() = minOrderAmount
}