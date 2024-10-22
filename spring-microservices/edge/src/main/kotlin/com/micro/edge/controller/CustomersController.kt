package com.micro.edge.controller

import com.micro.edge.dto.CustomerDetailsDto
import com.micro.edge.dto.ErrorResponseDto
import com.micro.edge.service.CustomersService
import com.micro.edge.validation.ValidMobileNumber
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.HttpURLConnection

@RefreshScope
@RestController
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
@Tag(name = "customers", description = "Customers API")
@ApiResponses(
    ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content = [Content(schema = Schema(implementation = ErrorResponseDto::class))]
    )
)
class CustomersController(
    private val customersService: CustomersService,
) {

    private val logger = LoggerFactory.getLogger(CustomersController::class.java)

    @Operation(summary = "Fetch customer details by mobile number")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Customer details fetched successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
            description = "Customer not found",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @GetMapping("/fetchCustomerDetails")
    suspend fun fetchCustomerDetails(
        @RequestHeader("correlation-id") correlationId: String?,
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<CustomerDetailsDto> {

        logger.debug("Correlation ID: $correlationId")

        return ResponseEntity.ok(customersService.fetchCustomerDetails(correlationId, mobileNumber))
    }
}