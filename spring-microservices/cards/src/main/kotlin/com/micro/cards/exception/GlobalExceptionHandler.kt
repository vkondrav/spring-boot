package com.micro.cards.exception

import com.micro.cards.dto.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CardAlreadyExistsException::class)
    fun handle(
        e: CardAlreadyExistsException,
        request: WebRequest,
    ): ResponseEntity<ErrorResponseDto> =
        ResponseEntity(
            ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                e.message ?: "",
                LocalDateTime.now(),
            ),
            HttpStatus.BAD_REQUEST
        )

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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<Map<String, String>> {

        val errors = ex.allErrors.mapNotNull { error ->
            val fieldName = (error as? FieldError)?.field ?: return@mapNotNull null
            val errorMessage = error.defaultMessage ?: return@mapNotNull null
            fieldName to errorMessage
        }.toMap()

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}