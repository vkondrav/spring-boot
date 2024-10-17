package com.micro.accounts.repositories

import com.micro.accounts.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {

     fun findByMobileNumber(mobileNumber: String): Customer?
}