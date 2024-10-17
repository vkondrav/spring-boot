package com.micro.accounts.dto

import com.micro.accounts.entities.Account
import com.micro.accounts.entities.Customer
import com.micro.accounts.validation.ValidMobileNumber
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.stereotype.Component

@Schema(
    name = "Customer",
    description = "Customer details"
)
data class CustomerDto(

    @Schema(description = "Customer name", example = "John Doe")
    @field:NotBlank(message = "Name is required")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val name: String,

    @Schema(description = "Customer email", example = "john.doe@email.com")
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be a valid email address")
    val email: String,

    @Schema(description = "Customer mobile number", example = "+1234567890")
    @field:ValidMobileNumber
    val mobileNumber: String,

    @Schema(description = "Customer account details")
    val accountDto: AccountDto?,
) {

    @Component
    class Builder(
        private val accountDtoBuilder: AccountDto.Builder
    ) {
        operator fun invoke(customer: Customer, account: Account) = CustomerDto(
            name = customer.name,
            email = customer.email,
            mobileNumber = customer.mobileNumber,
            accountDto = accountDtoBuilder(account)
        )
    }
}

