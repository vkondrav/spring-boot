package com.micro.cards.controller

import com.micro.cards.dto.*
import com.micro.cards.service.CardsService
import com.micro.cards.validation.ValidMobileNumber
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.HttpURLConnection

@RestController
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
@Tag(name = "Cards API", description = "Cards API")
@ApiResponses(
    ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content = [Content(schema = Schema(implementation = ErrorResponseDto::class))]
    )
)
class CardsController(
    private val cardsService: CardsService,
    @Value("\${build.version}")
    private val buildVersion: String,
    private val environment: Environment,
    private val contactInfoDto: ContactInfoDto,
) {

    @Operation(summary = "Create a card")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_CREATED.toString(),
            description = "Card created successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_BAD_REQUEST.toString(),
            description = "Card Already Exists",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        )
    )
    @PostMapping("/create")
    fun createCard(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<ResponseDto> {

        cardsService.createCard(mobileNumber)

        return ResponseEntity(
            ResponseDto(
                code = HttpURLConnection.HTTP_CREATED.toString(),
                message = "Card created successfully",
            ),
            HttpStatus.CREATED
        )
    }

    @Operation(summary = "Fetch card details by mobile number")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Card details fetched successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
            description = "Card not found",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @GetMapping("/fetch")
    fun fetchCardDetails(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<CardDto> =
        ResponseEntity(
            cardsService.fetchCard(mobileNumber),
            HttpStatus.OK
        )

    @Operation(summary = "Update a card")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Card updated successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
            description = "Card not updated",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @PutMapping("/update")
    fun updateCard(
        @Valid @RequestBody cardDto: CardDto,
    ): ResponseEntity<ResponseDto> {

        val updated = cardsService.updateCard(cardDto)

        return when (updated) {
            true -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_OK.toString(),
                    message = "Card updated successfully",
                ),
                HttpStatus.OK
            )

            false -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
                    message = "Card not updated",
                ),
                HttpStatus.NOT_MODIFIED
            )
        }
    }

    @Operation(summary = "Delete a card")
    @ApiResponses(
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_OK.toString(),
            description = "Card deleted successfully"
        ),
        ApiResponse(
            responseCode = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
            description = "Card not deleted",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponseDto::class)
                )
            ]
        ),
    )
    @DeleteMapping("/delete")
    fun deleteCard(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<ResponseDto> {
        val deleted = cardsService.deleteCard(mobileNumber)

        return when (deleted) {
            true -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_OK.toString(),
                    message = "Card deleted successfully",
                ),
                HttpStatus.OK
            )

            false -> ResponseEntity(
                ResponseDto(
                    code = HttpURLConnection.HTTP_NOT_MODIFIED.toString(),
                    message = "Card not deleted",
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