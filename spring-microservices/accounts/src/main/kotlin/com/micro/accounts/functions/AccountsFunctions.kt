package com.micro.accounts.functions

import com.micro.accounts.service.AccountsService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class AccountsFunctions(
    private val accountsService: AccountsService,
) {

    private val log = LoggerFactory.getLogger(AccountsFunctions::class.java)

    @Bean
    fun updateCommunication(): Consumer<Long> = Consumer { accountNumber ->
        log.info("Updating Communication for Account Number: {}", accountNumber)

        accountsService.updateCommunication(accountNumber)
    }
}