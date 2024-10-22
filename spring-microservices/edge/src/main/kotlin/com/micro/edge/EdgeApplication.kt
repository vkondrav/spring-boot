package com.micro.edge

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients


@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "Edge API",
        description = "Edge API",
        version = "1.0",
        contact = Contact(
            name = "Edge Microservice Team",
            email = "edge@microservices.com",
        ),
        license = License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    externalDocs = ExternalDocumentation(
        description = "Edge Wiki",
        url = "https://wiki.microservices.com/accounts"
    ),
    servers = [
        Server(url = "http://localhost:8072/", description = "Edge Service"),
    ],
)
@EnableFeignClients(
    basePackages = [
        "com.micro.cards.api",
        "com.micro.loans.api",
        "com.micro.accounts.api",
    ]
)
class EdgeApplication

fun main(args: Array<String>) {
    runApplication<EdgeApplication>(*args)
}

