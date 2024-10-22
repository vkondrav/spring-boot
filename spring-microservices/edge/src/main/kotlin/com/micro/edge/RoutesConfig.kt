package com.micro.edge

import org.apache.http.Header
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class RoutesConfig {
    @Bean
    fun routeConfig(builder: RouteLocatorBuilder): RouteLocator = builder.routes {
        route("accounts_route") {
            path("/micro/accounts/**")
            filters {
                rewritePath("/micro/accounts/(?<segment>.*)", "/\${segment}")
                addResponseHeader(
                    "X-Response-Time",
                    LocalDateTime.now().toString()
                )
            }
            uri("lb://ACCOUNTS")
        }
        route("accounts_docs_route") {
            path("/accounts/v3/api-docs")
            path("/accounts/v3/api-docs/**")
            uri("lb://ACCOUNTS")
        }
        route("cards_route") {
            path("/micro/cards/**")
            filters {
                rewritePath("/micro/cards/(?<segment>.*)", "/\${segment}")
                addResponseHeader(
                    "X-Response-Time",
                    LocalDateTime.now().toString()
                )
            }
            uri("lb://CARDS")
        }
        route("cards_docs_route") {
            path("/cards/v3/api-docs")
            path("/cards/v3/api-docs/**")
            uri("lb://CARDS")
        }
        route("loans_route") {
            path("/micro/loans/**")
            filters {
                rewritePath("/micro/loans/(?<segment>.*)", "/\${segment}")
                addResponseHeader(
                    "X-Response-Time",
                    LocalDateTime.now().toString()
                )
            }
            uri("lb://LOANS")
        }
        route("loans_docs_route") {
            path("/loans/v3/api-docs")
            path("/loans/v3/api-docs/**")
            uri("lb://LOANS")
        }
    }
}