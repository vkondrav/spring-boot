package com.micro.edge.service

import com.micro.accounts.api.AccountsApiClient
import com.micro.cards.api.CardsApiClient
import com.micro.edge.dto.CustomerDetailsDto
import com.micro.edge.exception.ResourceNotFoundException
import com.micro.loans.api.LoansApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Service
class CustomersService(
    private val accountsApiClient: AccountsApiClient,
    private val cardsApiClient: CardsApiClient,
    private val loansApiClient: LoansApiClient,
) {

    suspend fun fetchCustomerDetails(correlationId: String?, mobileNumber: String): CustomerDetailsDto = withContext(Dispatchers.IO) {

        val customer = suspendCoroutine {
            when (val result = accountsApiClient.fetchAccountDetails(mobileNumber, correlationId)?.body) {
                null -> it.resumeWithException(ResourceNotFoundException("Account", "Mobile Number", mobileNumber))
                else -> it.resume(result)
            }
        }

        val card = suspendCoroutine {
            it.resume(cardsApiClient.fetchCardDetails(mobileNumber, correlationId)?.body)
        }

        val loan = suspendCoroutine {
            it.resume(loansApiClient.fetchLoanDetails(mobileNumber, correlationId)?.body)
        }

        return@withContext CustomerDetailsDto(customer, card, loan)
    }

}