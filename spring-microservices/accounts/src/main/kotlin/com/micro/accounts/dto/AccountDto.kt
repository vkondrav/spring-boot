package com.micro.accounts.dto

import com.micro.accounts.entities.Account
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.springframework.stereotype.Component

@Schema(
    name = "Account",
    description = "Account details"
)
data class AccountDto(

    @Schema(description = "Account number")
    @NotEmpty(message = "Account number is required")
    @Pattern(regexp = "^[0-9][10]\$", message = "Account number must be a valid number")
    val accountNumber: Long,

    @Schema(description = "Account type", example = "Savings")
    @NotEmpty(message = "Account type is required")
    val accountType: String,

    @Schema(description = "Branch address", example = "123 Main St, City, Country")
    @NotEmpty(message = "Branch address is required")
    val branchAddress: String,
) {

    @Component
    class Builder {
        operator fun invoke(account: Account) = AccountDto(
            accountNumber = account.accountNumber,
            accountType = account.accountType,
            branchAddress = account.branchAddress
        )
    }
}
