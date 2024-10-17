package com.micro.accounts.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "contact")
data class ContactInfoDto (
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