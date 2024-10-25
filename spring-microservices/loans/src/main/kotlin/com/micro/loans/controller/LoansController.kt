package com.micro.loans.controller

import com.micro.loans.dto.*
import com.micro.loans.service.LoansService
import com.micro.loans.validation.ValidMobileNumber
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
@Tag(name = "loans", description = "Loans API")
@ApiResponses(
    ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content = [Content(schema = Schema(implementation = ErrorResponseDto::class))]
    )
)
class LoansController(
    private val loansService: LoansService,
    @Value("\${build.version}")
    private val buildVersion: String,
    private val environment: Environment,
    private val contactInfoDto: ContactInfoDto,
) {

    private val logger = LoggerFactory.getLogger(LoansController::class.java)

    @Operation(summary = "Create a loan")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_CREATED.toString(),
            description = "Loan created successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_BAD_REQUEST.toString(),
            description = "Loan Already Exists",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        )
    )
    @PostMapping("/create")
    fun createLoan(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<ResponseDto> {

        loansService.createLoan(mobileNumber)

        return ResponseEntity(
            ResponseDto(
                code = HttpURLConnection.HTTP_CREATED.toString(),
                message = "Card created successfully",
            ),
            HttpStatus.CREATED
        )
    }

    @Operation(summary = "Fetch loan details by mobile number")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Loan details fetched successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
            description = "Loan not found",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @GetMapping("/fetch")
    fun fetchLoanDetails(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<LoanDto> {

        logger.debug("Fetching loan details for mobile number: $mobileNumber")

        val response = loansService.fetchLoan(mobileNumber)

        logger.debug("Loan details fetched successfully for mobile number: $mobileNumber")

        return ResponseEntity.ok(response)
    }

    @Operation(summary = "Update a loan")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Loan updated successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
            description = "Loan not updated",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @PutMapping("/update")
    fun updateLoan(
        @Valid @RequestBody loanDto: LoanDto,
    ): ResponseEntity<ResponseDto> {

        val updated = loansService.updateLoan(loanDto)

        return when (updated) {
            true -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_OK.toString(),
                    message = "Loan updated successfully",
                ),
                HttpStatus.OK
            )

            false -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
                    message = "Loan not updated",
                ),
                HttpStatus.NOT_MODIFIED
            )
        }
    }

    @Operation(summary = "Delete a loan")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Loan deleted successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
            description = "Loan not deleted",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @DeleteMapping("/delete")
    fun deleteLoan(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<ResponseDto> {
        val deleted = loansService.deleteLoan(mobileNumber)

        return when (deleted) {
            true -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_OK.toString(),
                    message = "Loan deleted successfully",
                ),
                HttpStatus.OK
            )

            false -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
                    message = "Loan not deleted",
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