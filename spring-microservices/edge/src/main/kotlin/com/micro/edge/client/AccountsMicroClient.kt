package com.micro.edge.client

import com.micro.accounts.api.AccountsApi
import com.micro.accounts.model.BuildInfo
import com.micro.accounts.model.ContactInfoDto
import com.micro.accounts.model.Customer
import com.micro.accounts.model.Response
import com.micro.edge.config.FeignClientConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@FeignClient(name = "accounts", configuration = [FeignClientConfig::class], fallback = AccountsMicroClient.Fallback::class)
interface AccountsMicroClient : AccountsApi {

    @Component
    class Fallback : AccountsMicroClient {

        override fun fetchAccountDetails(mobileNumber: String?): ResponseEntity<Customer> =
            ResponseEntity.badRequest().body(null)

        override fun createAccount(customer: Customer?): ResponseEntity<Response> =
            ResponseEntity.badRequest().body(
                Response(
                    HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
                    "Service Unavailable",
                )
            )

        override fun deleteAccount(mobileNumber: String?): ResponseEntity<Response> =
            ResponseEntity.badRequest().body(
                Response(
                    HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
                    "Service Unavailable",
                )
            )

        override fun getBuildInfo(): ResponseEntity<BuildInfo> =
            ResponseEntity.badRequest().body(null)

        override fun getContactInfo(): ResponseEntity<ContactInfoDto> =
            ResponseEntity.badRequest().body(null)

        override fun updateAccount(customer: Customer?): ResponseEntity<Response> =
            ResponseEntity.badRequest().body(
                Response(
                    HttpStatus.SERVICE_UNAVAILABLE.value().toString(),
                    "Service Unavailable",
                )
            )
    }
}

