package com.micro.loans.entities

import jakarta.persistence.*
import org.springframework.stereotype.Component
import kotlin.random.Random

@Entity
data class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val loanId: Long,
    @Column(name = "mobile_number")
    val mobileNumber: String,
    @Column(name = "loan_number")
    val loanNumber: Long,
    @Column(name = "loan_type")
    val loanType: String,
    @Column(name = "total_loan")
    val totalLoan: Int,
    @Column(name = "amount_paid")
    val amountPaid: Int,
    @Column(name = "outstanding_amount")
    val outstandingAmount: Int,
) : BaseEntity() {

    @Component
    class Builder {
        operator fun invoke(mobileNumber: String) = Loan(
            loanId = 0,
            mobileNumber = mobileNumber,
            loanNumber = generateRandomLoanNumber(),
            loanType = "MORTGAGE",
            totalLoan = 100_000_000,
            amountPaid = 0,
            outstandingAmount = 100_000_000
        )

        private fun generateRandomLoanNumber(): Long {
            val random = Random.Default
            return (1..12)
                .map { random.nextInt(0, 10) }
                .joinToString("")
                .toLong()
        }
    }
}