package com.micro.cards.repositories

import com.micro.cards.entities.Card
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CardsRepository : JpaRepository<Card, Long> {
    fun findByMobileNumber(mobileNumber: String?): Card?

    fun findByCardNumber(cardNumber: String?): Card?
}