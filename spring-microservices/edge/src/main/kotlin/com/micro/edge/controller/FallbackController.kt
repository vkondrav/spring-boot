package com.micro.edge.controller

import com.micro.edge.dto.ErrorResponseDto
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class FallbackController {

    @Hidden
    @RequestMapping("/fallback")
    suspend fun fallback(): ResponseEntity<ErrorResponseDto> = ResponseEntity.internalServerError().body(
        ErrorResponseDto(
            "/fallback",
            HttpStatus.SERVICE_UNAVAILABLE,
            "Service unavailable. Please try again later.",
            LocalDateTime.now()
        )
    )
}