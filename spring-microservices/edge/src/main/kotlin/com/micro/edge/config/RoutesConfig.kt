package com.micro.edge.config

import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

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
                requestRateLimiter { config ->
                    config.setRateLimiter(redisRateLimiter())
                    config.setKeyResolver(userKeyResolver())
                }
                circuitBreaker { config ->
                    config.name = "accounts_circuit_breaker"
                    config.setFallbackUri("forward:/fallback")
                }
                retry { config ->
                    config.retries = 3
                    config.setMethods(HttpMethod.GET)
                    config.setBackoff(RetryGatewayFilterFactory.BackoffConfig().apply {
                        firstBackoff = 100.milliseconds.toJavaDuration()
                        maxBackoff = 1_000.milliseconds.toJavaDuration()
                        factor = 2
                        isBasedOnPreviousValue = true
                    })
                }
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
                requestRateLimiter { config ->
                    config.setRateLimiter(redisRateLimiter())
                    config.setKeyResolver(userKeyResolver())
                }
                circuitBreaker { config ->
                    config.name = "accounts_circuit_breaker"
                    config.setFallbackUri("forward:/fallback")
                }
                retry { config ->
                    config.retries = 3
                    config.setMethods(HttpMethod.GET)
                    config.setBackoff(RetryGatewayFilterFactory.BackoffConfig().apply {
                        firstBackoff = 100.milliseconds.toJavaDuration()
                        maxBackoff = 1_000.milliseconds.toJavaDuration()
                        factor = 2
                        isBasedOnPreviousValue = true
                    })
                }
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
                requestRateLimiter { config ->
                    config.setRateLimiter(redisRateLimiter())
                    config.setKeyResolver(userKeyResolver())
                }
                circuitBreaker { config ->
                    config.name = "accounts_circuit_breaker"
                    config.setFallbackUri("forward:/fallback")
                }
                retry { config ->
                    config.retries = 3
                    config.setMethods(HttpMethod.GET)
                    config.setBackoff(RetryGatewayFilterFactory.BackoffConfig().apply {
                        firstBackoff = 100.milliseconds.toJavaDuration()
                        maxBackoff = 1_000.milliseconds.toJavaDuration()
                        factor = 2
                        isBasedOnPreviousValue = true
                    })
                }
            }
            uri("lb://LOANS")
        }
        route("loans_docs_route") {
            path("/loans/v3/api-docs")
            path("/loans/v3/api-docs/**")
            uri("lb://LOANS")
        }
    }

    @Bean
    fun userKeyResolver(): KeyResolver = KeyResolver { exchange ->
        Mono.justOrEmpty(exchange.request.headers.getFirst("user"))
            .defaultIfEmpty("anonymous")
    }

    @Bean
    fun redisRateLimiter() = RedisRateLimiter(10, 10, 10)
}