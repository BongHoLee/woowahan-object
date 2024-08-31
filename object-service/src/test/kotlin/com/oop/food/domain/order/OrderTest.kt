package com.oop.food.domain.order

import com.oop.food.domain.shop.ShopRepository
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = ["classpath:application-test.yml"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderTest(
    var shopRepository: ShopRepository,
) : FunSpec({

    test("Order Entity로부터 데이터를 조회한다.") {
        val findById = shopRepository.findById(1L)
        println(findById)
    }
}) {

}