package com.micro.accounts.service

import com.micro.accounts.dto.CustomerDetailsDto
import com.micro.accounts.exception.ResourceNotFoundException
import com.micro.accounts.service.client.CardsFeignClient
import com.micro.accounts.service.client.LoansFeignClient
import org.springframework.stereotype.Service

@Service
class CustomersService(
    private val accountsService: AccountsService,
    private val cardsFeignClient: CardsFeignClient,
    private val loansFeignClient: LoansFeignClient,
) {

    fun fetchCustomerDetails(mobileNumber: String): CustomerDetailsDto {
        val customerDto = accountsService.fetchAccount(mobileNumber)

        val cardDto = cardsFeignClient.fetchCardDetails(mobileNumber).body ?: throw ResourceNotFoundException(
            "Card",
            "Mobile Number",
            mobileNumber
        )

        val loanDto = loansFeignClient.fetchLoanDetails(mobileNumber).body ?: throw ResourceNotFoundException(
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