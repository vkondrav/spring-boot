package com.micro.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Customer Details",
    description = "Customer, Account and Loand details"
)
data class CustomerDetailsDto(
    val customerDto: CustomerDto,
    val cardDto: CardDto,
    val loanDto: LoanDto,
)