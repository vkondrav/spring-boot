package com.micro.messages.dto

data class AccountsMessageDto(
    val accountNumber: Long = 0L,
    val name: String = "",
    val email: String = "",
    val mobileNumber: String = "",
)
