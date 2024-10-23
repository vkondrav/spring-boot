package com.micro.edge.client


import com.micro.loans.model.BuildInfo
import com.micro.loans.model.ContactInfoDto
import com.micro.loans.model.Loan
import com.micro.loans.model.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class LoansMicroClientFallback : LoansMicroClient {
    override fun createLoan(mobileNumber: String?): ResponseEntity<Response> = ResponseEntity.badRequest().body(
        Response(
            HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
            "Service Unavailable",
        )
    )

    override fun deleteLoan(mobileNumber: String?): ResponseEntity<Response> = ResponseEntity.badRequest().body(
        Response(
            HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
            "Service Unavailable",
        )
    )

    override fun fetchLoanDetails(mobileNumber: String?, correlationId: String?): ResponseEntity<Loan> =
        ResponseEntity.badRequest().body(null)

    override fun getBuildInfo(): ResponseEntity<BuildInfo> = ResponseEntity.badRequest().body(null)

    override fun getContactInfo(): ResponseEntity<ContactInfoDto> = ResponseEntity.badRequest().body(null)

    override fun updateLoan(loan: Loan?): ResponseEntity<Response> = ResponseEntity.badRequest().body(
        Response(
            HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
            "Service Unavailable",
        )
    )
}