package com.micro.edge.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Schema(
    name = "ErrorResponse",
    description = "Failure response information"
)
data class ErrorResponseDto(

    @Schema(description = "API path")
    val apiPath: String,

    @Schema(description = "HTTP status code")
    val code: HttpStatus,
    
    val message: String,
    val time: LocalDateTime,
)
