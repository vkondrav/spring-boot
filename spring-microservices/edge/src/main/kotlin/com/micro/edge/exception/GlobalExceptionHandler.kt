package com.micro.edge.exception

import com.micro.edge.dto.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handle(
        e: ResourceNotFoundException,
        request: WebRequest,
    ): ResponseEntity<ErrorResponseDto> =
        ResponseEntity(
            ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                e.message ?: "",
                LocalDateTime.now(),
            ),
            HttpStatus.NOT_FOUND
        )

    @ExceptionHandler(Exception::class)
    fun handle(e: Exception, request: WebRequest): ResponseEntity<ErrorResponseDto> =
        ResponseEntity(
            ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.message ?: "",
                LocalDateTime.now(),
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
}