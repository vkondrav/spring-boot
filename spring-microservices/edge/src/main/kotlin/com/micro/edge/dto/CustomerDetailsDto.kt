package com.micro.edge.dto

import com.micro.accounts.model.Account
import com.micro.accounts.model.Customer
import com.micro.cards.model.Card
import com.micro.loans.model.Loan
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "CustomerDetails",
    description = "Customer, Account and Loan details"
)
data class CustomerDetailsDto(
    val customerDto: Customer,
    val cardDto: Card?,
    val loanDto: Loan?,
)