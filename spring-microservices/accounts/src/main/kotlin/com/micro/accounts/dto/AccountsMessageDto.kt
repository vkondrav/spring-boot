package com.micro.accounts.dto

data class AccountsMessageDto(
    val accountNumber: Long = 0L,
    val name: String = "",
    val email: String = "",
    val mobileNumber: String = "",
)
