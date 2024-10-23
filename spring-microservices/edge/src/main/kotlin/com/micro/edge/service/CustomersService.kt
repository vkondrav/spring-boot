package com.micro.edge.service

import com.micro.edge.client.AccountsMicroClient
import com.micro.edge.client.CardsMicroClient
import com.micro.edge.client.LoansMicroClient
import com.micro.edge.dto.CustomerDetailsDto
import com.micro.edge.exception.ResourceNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Service
class CustomersService(
    private val accountsApiClient: AccountsMicroClient,
    private val cardsApiClient: CardsMicroClient,
    private val loansApiClient: LoansMicroClient,
) {

    suspend fun fetchCustomerDetails(correlationId: String?, mobileNumber: String): CustomerDetailsDto =
        withContext(Dispatchers.IO) {

            val customer = suspendCoroutine {
                try {
                    val response = accountsApiClient.fetchAccountDetails(mobileNumber, correlationId)

                    val code = response.statusCode
                    val body = response.body

                    when {
                        code == HttpStatus.OK && body != null -> it.resume(body)
                        else -> it.resumeWithException(
                            ResourceNotFoundException(
                                "Account",
                                "Mobile Number",
                                mobileNumber
                            )
                        )
                    }

                } catch (e: Exception) {
                    it.resumeWithException(e)
                }
            }

            val card = suspendCoroutine {
                try {
                    it.resume(cardsApiClient.fetchCardDetails(mobileNumber, correlationId)?.body)
                } catch (e: Exception) {
                    it.resume(null)
                }
            }

            val loan = suspendCoroutine {
                try {
                    it.resume(loansApiClient.fetchLoanDetails(mobileNumber, correlationId)?.body)
                } catch (e: Exception) {
                    it.resume(null)
                }
            }

            return@withContext CustomerDetailsDto(customer, card, loan)
        }

}