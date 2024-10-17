package com.library.spring.demo

import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.Paths
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openApiCustomizer() = OpenApiCustomizer { openApi ->
        openApi.paths = openApi.paths
            .filter { it.key.startsWith(API_VERSION) }
            .paths
    }

    private val Map<String, PathItem>.paths: Paths
        get() = Paths().also { paths ->
            paths.putAll(this)
        }
}

internal const val API_VERSION = "/v1"