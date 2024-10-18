package com.micro.accounts.service.client

import com.micro.accounts.dto.LoanDto
import com.micro.accounts.validation.ValidMobileNumber
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "loans")
interface LoansFeignClient {

    @GetMapping("/api/fetch", consumes = ["application/json"])
    fun fetchLoanDetails(
        @ValidMobileNumber @RequestParam mobileNumber: String,
    ): ResponseEntity<LoanDto>

}