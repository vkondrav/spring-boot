package com.micro.loans.service

import com.micro.loans.dto.LoanDto
import com.micro.loans.exception.LoanAlreadyExistsException
import com.micro.loans.exception.ResourceNotFoundException
import com.micro.loans.entities.Loan
import com.micro.loans.repositories.LoansRepository
import org.springframework.stereotype.Service

@Service
class LoansService(
    private val loansRepository: LoansRepository,
    private val loanBuilder: Loan.Builder,
    private val loanDtoBuilder: LoanDto.Builder,
) {

    fun createLoan(mobileNumber: String) {
        val existingLoan = loansRepository.findByMobileNumber(mobileNumber)

        when (existingLoan) {
            null -> loansRepository.save(loanBuilder(mobileNumber))
            else -> throw LoanAlreadyExistsException("Loan with mobile number $mobileNumber already exists")
        }
    }

    fun fetchLoan(mobileNumber: String): LoanDto {
        val loan = loansRepository.findByMobileNumber(mobileNumber)
            ?: throw ResourceNotFoundException("Loan", "Mobile Number", mobileNumber)

        return loanDtoBuilder(loan)
    }

    fun updateLoan(loanDto: LoanDto): Boolean {

        val loan = loansRepository.findByLoanNumber(loanDto.loanNumber)
            ?: throw ResourceNotFoundException("Loan", "Mobile Number", loanDto.mobileNumber)

        val updatedLoan = loan.copy(
            loanType = loanDto.loanType,
            totalLoan = loanDto.totalLoan,
            amountPaid = loanDto.amountPaid,
            outstandingAmount = loanDto.outstandingAmount
        )

        loansRepository.save(updatedLoan)
        return true
    }

    fun deleteLoan(mobileNumber: String): Boolean {
        val card = loansRepository.findByMobileNumber(mobileNumber)
            ?: throw ResourceNotFoundException("Loan", "Mobile Number", mobileNumber)

        loansRepository.delete(card)
        return true
    }
}