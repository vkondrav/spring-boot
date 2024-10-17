package com.micro.cards.service

import com.micro.cards.dto.CardDto
import com.micro.cards.entities.Card
import com.micro.cards.exception.CardAlreadyExistsException
import com.micro.cards.exception.ResourceNotFoundException
import com.micro.cards.repositories.CardsRepository
import org.springframework.stereotype.Service

@Service
class CardsService(
    private val cardsRepository: CardsRepository,
    private val cardBuilder: Card.Builder,
    private val cardDtoBuilder: CardDto.Builder,
) {

    fun createCard(mobileNumber: String) {
        val existingCard = cardsRepository.findByMobileNumber(mobileNumber)

        when (existingCard) {
            null -> cardsRepository.save(cardBuilder(mobileNumber))
            else -> throw CardAlreadyExistsException("Card with mobile number $mobileNumber already exists")
        }
    }

    fun fetchCard(mobileNumber: String): CardDto {
        val card = cardsRepository.findByMobileNumber(mobileNumber)
            ?: throw ResourceNotFoundException("Card", "Mobile Number", mobileNumber)

        return cardDtoBuilder(card)
    }

    fun updateCard(cardDto: CardDto): Boolean {

        val card = cardsRepository.findByCardNumber(cardDto.cardNumber)
            ?: throw ResourceNotFoundException("Card", "Card Number", cardDto.mobileNumber)

        val updatedCard = card.copy(
            cardType = cardDto.cardType,
            totalLimit = cardDto.totalLimit,
            amountUsed = cardDto.amountUsed,
            availableAmount = cardDto.availableAmount
        )

        cardsRepository.save(updatedCard)
        return true
    }

    fun deleteCard(mobileNumber: String): Boolean {
        val card = cardsRepository.findByMobileNumber(mobileNumber)
            ?: throw ResourceNotFoundException("Card", "Mobile Number", mobileNumber)

        cardsRepository.delete(card)
        return true
    }
}