package com.micro.loans.repositories

import com.micro.loans.entities.Loan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface LoansRepository : JpaRepository<Loan, Long> {
    fun findByMobileNumber(mobileNumber: String): Loan?

    fun findByLoanNumber(loanNumber: Long): Loan?
}