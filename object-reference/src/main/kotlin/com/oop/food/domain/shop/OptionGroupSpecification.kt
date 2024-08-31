package com.oop.food.domain.shop

import jakarta.persistence.*

@Entity
@Table(name = "OPTION_GROUP_SPECS")
class OptionGroupSpecification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_GROUP_SPEC_ID")
    private var id: Long?,

    @Column(name = "NAME")
    private var name: String,

    // 여러 옵션 선택 가능 여부
    @Column(name = "EXCLUSIVE")
    private var exclusive: Boolean,

    @Column(name = "BASIC")
    private var basic: Boolean,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "OPTION_GROUP_SPEC_ID")
    private var optionSpecs: MutableList<OptionSpecification> = mutableListOf()

) {
    fun isSatisfiedBy(optionGroup: OptionGroup): Boolean =
        !isSatisfied(optionGroup.name, satisfied(optionGroup.options))

    private fun isSatisfied(groupName: String, satisfied: List<Option>): Boolean {
        return when {
            name != groupName -> false
            satisfied.isEmpty() -> false
            exclusive && satisfied.size > 1 -> false
            else -> true
        }
    }

    // OptionGroup의 options를 OptionSpecification과 비교하여 만족하는 Option을 반환
    private fun satisfied(options: List<Option>): List<Option> =
        optionSpecs.flatMap { eachOptionSpec ->
            options.filter { eachOption -> eachOptionSpec.isSatisfiedBy(eachOption) }
        }
}