package com.micro.accounts.service

import com.micro.accounts.dto.AccountDto
import com.micro.accounts.dto.AccountsMessageDto
import com.micro.accounts.dto.CustomerDto
import com.micro.accounts.entities.Account
import com.micro.accounts.entities.Customer
import com.micro.accounts.exception.CustomerAlreadyExistsException
import com.micro.accounts.exception.ResourceNotFoundException
import com.micro.accounts.repositories.AccountRepository
import com.micro.accounts.repositories.CustomerRepository
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service


@Service
class AccountsService(
    private val accountRepository: AccountRepository,
    private val customerRepository: CustomerRepository,
    private val customerBuilder: Customer.Builder,
    private val customerDtoBuilder: CustomerDto.Builder,
    private val accountBuilder: Account.Builder,
    private val streamBridge: StreamBridge,
) {

    private val log = LoggerFactory.getLogger(AccountsService::class.java)

    fun createAccount(customerDto: CustomerDto) {

        val potentialCustomer = customerBuilder(customerDto)

        val customerAlreadyExists = customerRepository
            .findByMobileNumber(potentialCustomer.mobileNumber) != null

        if (customerAlreadyExists) {
            throw CustomerAlreadyExistsException(
                "Customer with mobile number ${potentialCustomer.mobileNumber} already exists"
            )
        }

        val newCustomer = customerRepository.save(potentialCustomer)
        val newAccount = accountRepository.save(accountBuilder(newCustomer))

        sendCommunication(newAccount, newCustomer)
    }

    private fun sendCommunication(account: Account, customer: Customer) {

        val accountsMsgDto = AccountsMessageDto(
            account.accountNumber,
            customer.name,
            customer.email,
            customer.mobileNumber
        )

        log.info("Sending Communication request for the details: {}", accountsMsgDto)

        val result = streamBridge.send("sendCommunication-out-0", accountsMsgDto)

        log.info("Is the Communication request successfully triggered ? : {}", result)
    }

    fun fetchAccount(mobileNumber: String): CustomerDto {
        val customer = customerRepository.findByMobileNumber(mobileNumber)
            ?: throw ResourceNotFoundException(
                "Customer",
                "Mobile Number",
                mobileNumber
            )

        val account = accountRepository.findByCustomerId(customer.customerId)
            ?: throw ResourceNotFoundException(
                "Account",
                "Customer ID",
                customer.customerId.toString()
            )

        return customerDtoBuilder(customer, account)
    }

    fun updateAccount(customerDto: CustomerDto): Boolean {

        val accountDto = customerDto.accountDto ?: throw ResourceNotFoundException(
            "CustomerDto",
            "AccountDto",
            "null"
        )

        return updateAccount(accountDto) && updateCustomer(customerDto)
    }

    private fun updateAccount(accountDto: AccountDto): Boolean {

        val account = accountRepository.findById(accountDto.accountNumber)
            .orElseThrow {
                ResourceNotFoundException(
                    "Account",
                    "Account Number",
                    accountDto.accountNumber.toString()
                )
            }

        val updatedAccount = account.copy(
            accountType = accountDto.accountType,
            branchAddress = accountDto.branchAddress,
        )

        accountRepository.save(updatedAccount)

        return true
    }

    private fun updateCustomer(customerDto: CustomerDto): Boolean {

        val customer = customerRepository.findByMobileNumber(customerDto.mobileNumber)
            ?: throw ResourceNotFoundException(
                "Customer",
                "Mobile Number",
                customerDto.mobileNumber
            )

        val updatedCustomer = customer.copy(
            name = customerDto.name,
            email = customerDto.email,
            mobileNumber = customerDto.mobileNumber,
        )

        customerRepository.save(updatedCustomer)

        return true
    }

    fun deleteAccount(mobileNumber: String): Boolean {
        val customer = customerRepository.findByMobileNumber(mobileNumber)
            ?: throw ResourceNotFoundException(
                "Customer",
                "Mobile Number",
                mobileNumber
            )

        accountRepository.deleteByCustomerId(customer.customerId)
        customerRepository.deleteById(customer.customerId)

        return true
    }

    fun updateCommunication(accountNumber: Long) {
        val account = accountRepository.findById(accountNumber)
            .orElseThrow {
                ResourceNotFoundException(
                    "Account",
                    "Account Number",
                    accountNumber.toString()
                )
            }

        accountRepository.save(account.copy(communicationSw = true))
    }
}