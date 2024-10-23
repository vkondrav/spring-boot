package com.micro.accounts.controller

import com.micro.accounts.dto.*
import com.micro.accounts.validation.ValidMobileNumber
import com.micro.accounts.service.AccountsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.HttpURLConnection

@RefreshScope
@RestController
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
@Tag(name = "account", description = "Accounts API")
@ApiResponses(
    ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content = [Content(schema = Schema(implementation = ErrorResponseDto::class))]
    )
)
class AccountsController(
    private val accountsService: AccountsService,
    @Value("\${build.version}")
    private val buildVersion: String,
    private val environment: Environment,
    private val contactInfoDto: ContactInfoDto,
) {

    private val logger = LoggerFactory.getLogger(AccountsController::class.java)

    @Operation(summary = "Create an account")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_CREATED.toString(),
            description = "Account created successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_BAD_REQUEST.toString(),
            description = "Customer Already Exists",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        )
    )
    @PostMapping("/create")
    fun createAccount(
        @Valid @RequestBody customerDto: CustomerDto,
    ): ResponseEntity<ResponseDto> {

        accountsService.createAccount(customerDto)

        return ResponseEntity(
            ResponseDto(
                code = HttpURLConnection.HTTP_CREATED.toString(),
                message = "Account created successfully",
            ),
            HttpStatus.CREATED
        )
    }

    @Operation(summary = "Fetch account details by mobile number")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Account details fetched successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
            description = "Account not found",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @GetMapping("/fetch")
    fun fetchAccountDetails(
        @RequestHeader("correlation-id") correlationId: String?,
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<CustomerDto> {

        logger.debug("Correlation ID: $correlationId")

        return ResponseEntity(
            accountsService.fetchAccount(mobileNumber),
            HttpStatus.OK
        )
    }

    @Operation(summary = "Update an account")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Account updated successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
            description = "Account not updated",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @PutMapping("/update")
    fun updateAccount(
        @Valid @RequestBody customerDto: CustomerDto,
    ): ResponseEntity<ResponseDto> {

        val updated = accountsService.updateAccount(customerDto)

        return when (updated) {
            true -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_OK.toString(),
                    message = "Account updated successfully",
                ),
                HttpStatus.OK
            )

            false -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
                    message = "Account not updated",
                ),
                HttpStatus.NOT_MODIFIED
            )
        }
    }

    @Operation(summary = "Delete an account")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Account deleted successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
            description = "Account not deleted",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @DeleteMapping("/delete")
    fun deleteAccount(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<ResponseDto> {
        val deleted = accountsService.deleteAccount(mobileNumber)

        return when (deleted) {
            true -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_OK.toString(),
                    message = "Account deleted successfully",
                ),
                HttpStatus.OK
            )

            false -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
                    message = "Account not deleted",
                ),
                HttpStatus.NOT_MODIFIED
            )
        }
    }

    @Operation(summary = "Get build info")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Build info fetched successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
            description = "Build info not found",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @GetMapping("/build-info")
    fun getBuildInfo(): ResponseEntity<BuildInfoDto> =
        ResponseEntity(
            BuildInfoDto(
                appVersion = buildVersion,
                javaVersion = environment.getProperty("JAVA_HOME") ?: "Unknown",
            ),
            HttpStatus.OK
        )

    @Operation(summary = "Get contact info")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Contact Info fetched successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
            description = "Contact info not found",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @GetMapping("contact-info")
    fun getContactInfo(): ResponseEntity<ContactInfoDto> =
        ResponseEntity(
            contactInfoDto,
            HttpStatus.OK
        )
}