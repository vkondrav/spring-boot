package com.micro.accounts.dto

import com.micro.cards.model.Card
import com.micro.loans.model.Loan
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Customer Details",
    description = "Customer, Account and Loan details"
)
data class CustomerDetailsDto(
    val customerDto: CustomerDto,
    val cardDto: Card,
    val loanDto: Loan,
)