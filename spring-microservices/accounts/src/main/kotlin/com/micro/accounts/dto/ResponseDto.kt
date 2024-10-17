package com.micro.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Response",
    description = "Success response information"
)
data class ResponseDto(
    val code: String,
    val message: String,
)
