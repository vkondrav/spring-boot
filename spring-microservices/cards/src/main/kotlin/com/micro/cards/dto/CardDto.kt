package com.micro.cards.dto

import com.micro.cards.entities.Card
import com.micro.cards.validation.ValidMobileNumber
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.stereotype.Component


@Schema(name = "Cards", description = "Schema to hold Card information")
data class CardDto(
    @NotBlank(message = "Mobile Number can not be a null or empty")
    @ValidMobileNumber
    @Schema(description = "Mobile Number of Customer", example = "4354437687")
    val mobileNumber: String,

    @NotBlank(message = "Card Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "CardNumber must be 12 digits")
    @Schema(description = "Card Number of the customer", example = "100646930341")
    val cardNumber: String,

    @NotBlank(message = "CardType can not be a null or empty")
    @Schema(description = "Type of the card", example = "Credit Card")
    val cardType: String,

    @Schema(description = "Total amount limit available against a card", example = "100000")
    @Positive(message = "Total card limit should be greater than zero")
    val totalLimit: Int,

    @Schema(description = "Total amount used by a Customer", example = "1000")
    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    val amountUsed: Int,

    @Schema(description = "Total available amount against a card", example = "90000")
    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    val availableAmount: Int,
) {

    @Component
    class Builder {
        operator fun invoke(card: Card) = CardDto(
            mobileNumber = card.mobileNumber,
            cardNumber = card.cardNumber,
            cardType = card.cardType,
            totalLimit = card.totalLimit,
            amountUsed = card.amountUsed,
            availableAmount = card.availableAmount,
        )
    }
}