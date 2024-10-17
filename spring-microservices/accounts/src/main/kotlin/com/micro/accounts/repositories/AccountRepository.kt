package com.micro.accounts.repositories

import com.micro.accounts.entities.Account
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: JpaRepository<Account, Long> {

    fun findByCustomerId(customerId: Long): Account?

    @Transactional
    @Modifying
    fun deleteByCustomerId(customerId: Long)
}