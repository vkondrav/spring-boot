package com.micro.edge.config

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Order(1)
@Component
class RequestTraceFilter : GlobalFilter {

    private val logger = LoggerFactory.getLogger(RequestTraceFilter::class.java)

    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void> {
        val requestHeaders = exchange?.request?.headers ?: return Mono.empty()

        if (requestHeaders.isCorrelationIdPresent) {
            logger.debug(
                "Correlation ID found in the incoming request: {}",
                requestHeaders[CORRELATION_ID]?.firstOrNull() ?: "unknown"
            )
        } else {
            val correlationId = generateCorrelationId()

            exchange.mutate().request {
                it.headers { headers ->
                    headers.set(CORRELATION_ID, correlationId)
                }.build()
            }.build()

            logger.debug("Correlation ID generated: {}", correlationId)
        }

        return chain?.filter(exchange) ?: Mono.empty()
    }

    private fun generateCorrelationId() = java.util.UUID.randomUUID().toString()
}

@Configuration
class ResponseTraceFiler {

    private val logger = LoggerFactory.getLogger(ResponseTraceFiler::class.java)

    @Bean
    fun globalPostFilter(): GlobalFilter = GlobalFilter { exchange, chain ->
        val requestHeaders = exchange.request.headers

        val correlationId = requestHeaders[CORRELATION_ID]?.firstOrNull() ?: "unknown"

        logger.debug("Updating the correlation ID in the response: {}", correlationId)

        exchange.response.headers.add(CORRELATION_ID, correlationId)

        chain.filter(exchange)
    }
}

private val HttpHeaders.isCorrelationIdPresent: Boolean
    get() = get(CORRELATION_ID) != null

private const val CORRELATION_ID = "micro-correlation-id"