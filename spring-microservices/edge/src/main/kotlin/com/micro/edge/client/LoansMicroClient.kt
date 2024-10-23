package com.micro.edge.client

import com.micro.loans.api.LoansApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "loans", fallback = LoansMicroClientFallback::class)
interface LoansMicroClient : LoansApi

