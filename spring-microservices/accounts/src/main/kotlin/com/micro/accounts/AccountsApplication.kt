package com.micro.accounts

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@OpenAPIDefinition(
    info = Info(
        title = "Accounts API",
        description = "Accounts API",
        version = "1.0",
        contact = Contact(
            name = "Accounts Microservice Team",
            email = "account@microservices.com",
        ),
        license = License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    externalDocs = ExternalDocumentation(
        description = "Accounts Wiki",
        url = "https://wiki.microservices.com/accounts"
    ),
    servers = [
        Server(url = "http://localhost:8072/micro/accounts", description = "Accounts Edge"),
    ],
)
@ConfigurationPropertiesScan
class AccountsApplication

fun main(args: Array<String>) {
    runApplication<AccountsApplication>(*args)
}
