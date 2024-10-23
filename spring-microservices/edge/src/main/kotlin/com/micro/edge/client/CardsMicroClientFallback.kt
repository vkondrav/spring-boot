package com.micro.edge.client

import com.micro.cards.model.BuildInfo
import com.micro.cards.model.Card
import com.micro.cards.model.ContactInfoDto
import com.micro.cards.model.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class CardsMicroClientFallback : CardsMicroClient {
    override fun createCard(mobileNumber: String?): ResponseEntity<Response> = ResponseEntity.badRequest().body(
        Response(
            HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
            "Service Unavailable",
        )
    )

    override fun deleteCard(mobileNumber: String?): ResponseEntity<Response> = ResponseEntity.badRequest().body(
        Response(
            HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
            "Service Unavailable",
        )
    )

    override fun fetchCardDetails(mobileNumber: String?, correlationId: String?): ResponseEntity<Card> =
        ResponseEntity.badRequest().body(null)

    override fun getBuildInfo(): ResponseEntity<BuildInfo> =
        ResponseEntity.badRequest().body(null)

    override fun getContactInfo(): ResponseEntity<ContactInfoDto> = ResponseEntity.badRequest().body(null)

    override fun updateCard(card: Card?): ResponseEntity<Response> = ResponseEntity.badRequest().body(
        Response(
            HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
            "Service Unavailable",
        )
    )

}