package com.micro.cards.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    resourceName: String,
    fieldName: String,
    fieldValue: String,
) : RuntimeException(
    "$resourceName not found with $fieldName : '$fieldValue'"
)