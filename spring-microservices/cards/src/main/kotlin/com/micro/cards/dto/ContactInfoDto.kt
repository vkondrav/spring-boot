package com.micro.cards.dto

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.cloud.context.config.annotation.RefreshScope

@ConfigurationProperties(prefix = "contact")
data class ContactInfoDto(
    val message: String,
    val contactDetails: ContactDetails,
    val onCall: List<ContactDetails>,
) {
    data class ContactDetails(
        val name: String,
        val email: String,
        val phone: String,
    )
}