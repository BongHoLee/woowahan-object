package com.oop.food.domain.shop

import jakarta.persistence.*

@Entity
@Table(name = "MENUS")
class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private var id: Long?,

    @Column(name="FOOD_NAME")
    private var name: String,

    @Column(name="FOOD_DESCRIPTION")
    private var description: String,

    // shop에 대한 객체참조를 끊고 id만 참조하도록 변경
    @Column(name="MENU_ID")
    private var shopId: Long,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name="MENU_ID")
    private var optionGroupSpecs: MutableList<OptionGroupSpecification> = mutableListOf()
) {

    fun validateOrder(orderingMenuName: String, optionGroups: List<OptionGroup>) {
        if (orderingMenuName != this.name) {
            throw IllegalArgumentException("기본 상품이 변경됐습니다.")
        }

        if (!isSatisfiedBy(optionGroups)) {
            throw IllegalArgumentException("메뉴가 변경되었습니다.")
        }
    }

    fun id(): Long? = id

    fun name(): String = name

    fun optionGroupSpecs(): List<OptionGroupSpecification> = optionGroupSpecs

    private fun isSatisfiedBy(cartOptionGroups: List<OptionGroup>): Boolean =
        cartOptionGroups.any { isSatisfiedBy(it) }

    private fun isSatisfiedBy(group: OptionGroup): Boolean =
        optionGroupSpecs.any { it.isSatisfiedBy(group) }
}