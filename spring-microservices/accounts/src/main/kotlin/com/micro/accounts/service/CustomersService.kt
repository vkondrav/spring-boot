package com.micro.accounts.service

import com.micro.accounts.dto.CustomerDetailsDto
import com.micro.accounts.exception.ResourceNotFoundException
import com.micro.cards.api.CardsApiClient
import com.micro.loans.api.LoansApiClient
import org.springframework.stereotype.Service

@Service
class CustomersService(
    private val accountsService: AccountsService,
    private val cardsFeignClient: CardsApiClient,
    private val loansFeignClient: LoansApiClient,
) {

    fun fetchCustomerDetails(correlationId: String?, mobileNumber: String): CustomerDetailsDto {
        val customerDto = accountsService.fetchAccount(mobileNumber)

        val cardDto = cardsFeignClient.fetchCardDetails(
            correlationId,
            mobileNumber
        ).body ?: throw ResourceNotFoundException(
            "Card",
            "Mobile Number",
            mobileNumber
        )

        val loanDto = loansFeignClient.fetchLoanDetails(
            correlationId,
            mobileNumber
        ).body ?: throw ResourceNotFoundException(
            "Loan",
            "Mobile Number",
            mobileNumber
        )

        return CustomerDetailsDto(
            customerDto,
            cardDto,
            loanDto
        )
    }
}