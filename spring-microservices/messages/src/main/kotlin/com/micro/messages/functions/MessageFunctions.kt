package com.micro.messages.functions

import com.micro.messages.dto.AccountsMessageDto
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageFunctions {

    private val log = LoggerFactory.getLogger(MessageFunctions::class.java)

    @Bean
    fun email(): (AccountsMessageDto) -> AccountsMessageDto = { message ->
        log.info("Sending email to ${message.email}")
        message
    }

    @Bean
    fun sms(): (AccountsMessageDto) -> Long = { message ->
        log.info("Sending sms to ${message.mobileNumber}")
        message.accountNumber
    }
}
