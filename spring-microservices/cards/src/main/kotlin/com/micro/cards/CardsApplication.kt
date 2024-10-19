package com.micro.cards

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@OpenAPIDefinition(
    info = Info(
        title = "Cards API",
        description = "Cards API",
        version = "1.0",
        contact = Contact(
            name = "Cards Microservice Team",
            email = "cards@microservices.com",
        ),
        license = License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    externalDocs = ExternalDocumentation(
        description = "Cards Wiki",
        url = "https://wiki.microservices.com/cards"
    )
)
@Tag(name = "cards", description = "Cards API")
@ConfigurationPropertiesScan
class CardsApplication

fun main(args: Array<String>) {
    runApplication<CardsApplication>(*args)
}
