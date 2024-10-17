package com.micro.accounts.entities

import com.micro.accounts.dto.CustomerDto
import jakarta.persistence.*
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    val customerId: Long = 0L,
    @Column(name = "name")
    val name: String,
    @Column(name = "email")
    val email: String,
    @Column(name = "mobile_number")
    val mobileNumber: String,
) : BaseEntity() {

    @Component
    class Builder {
        operator fun invoke(customerDto: CustomerDto) = Customer(
            name = customerDto.name,
            email = customerDto.email,
            mobileNumber = customerDto.mobileNumber
        )
    }
}
