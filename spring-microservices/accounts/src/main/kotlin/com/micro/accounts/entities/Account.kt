package com.micro.accounts.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.stereotype.Component
import kotlin.random.Random

@Entity
data class Account(
    @Id
    @Column(name = "account_number")
    val accountNumber: Long,
    @Column(name = "customer_id")
    val customerId: Long,
    @Column(name = "account_type")
    val accountType: String,
    @Column(name = "branch_address")
    val branchAddress: String,
) : BaseEntity() {

    @Component
    class Builder {

        private val addresses = listOf(
            "123 Main St, Springfield",
            "456 Elm St, Shelbyville",
            "789 Oak St, Capital City",
            "101 Maple St, Ogdenville",
            "202 Pine St, North Haverbrook",
        )

        private val accountTypes = listOf(
            "SAVINGS",
            "CHECKING",
            "BUSINESS",
            "JOINT"
        )

        operator fun invoke(customer: Customer) = Account(
            accountNumber = Random.nextLong(1_000_000_000_000, 9_999_999_999_999),
            customerId = customer.customerId,
            accountType = accountTypes.random(),
            branchAddress = addresses.random()
        )
    }
}
