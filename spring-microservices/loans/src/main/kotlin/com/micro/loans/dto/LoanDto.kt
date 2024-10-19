package com.micro.loans.dto

import com.micro.loans.entities.Loan
import com.micro.loans.validation.ValidMobileNumber
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.stereotype.Component


@Schema(name = "Loan", description = "Schema to hold Loan information")
data class LoanDto(
    @NotBlank(message = "Mobile Number can not be a null or empty")
    @ValidMobileNumber
    @Schema(description = "Mobile Number of Customer", example = "4354437687")
    val mobileNumber: String,

    @NotBlank(message = "Loan Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "LoanNumber must be 12 digits")
    @Schema(
        description = "Loan Number of the customer", example = "548732457654"
    )
    val loanNumber: Long,

    @NotBlank(message = "LoanType can not be a null or empty")
    @Schema(
        description = "Type of the loan", example = "Home Loan"
    )
    val loanType: String,

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
        description = "Total loan amount", example = "100000"
    )
    val totalLoan: Int,

    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
        description = "Total loan amount paid", example = "1000"
    )
    val amountPaid: Int,

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(
        description = "Total outstanding amount against a loan", example = "99000"
    )
    val outstandingAmount: Int,
) {

    @Component
    class Builder {
        operator fun invoke(card: Loan) = LoanDto(
            mobileNumber = card.mobileNumber,
            loanNumber = card.loanNumber,
            loanType = card.loanType,
            totalLoan = card.totalLoan,
            amountPaid = card.amountPaid,
            outstandingAmount = card.outstandingAmount,
        )
    }
}