package com.micro.accounts.controller

import com.micro.accounts.dto.CustomerDetailsDto
import com.micro.accounts.dto.ErrorResponseDto
import com.micro.accounts.service.CustomersService
import com.micro.accounts.validation.ValidMobileNumber
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.HttpURLConnection

@RefreshScope
@RestController
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
@Tag(name = "Customers API", description = "Customers API")
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
    fun fetchCustomerDetails(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<CustomerDetailsDto> = ResponseEntity.ok(
        customersService.fetchCustomerDetails(mobileNumber),
    )

}