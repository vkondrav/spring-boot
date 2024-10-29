package com.micro.edge.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    /**
     * login	worrisome-scarab@example.com
     * password	Xerothermic-Anteater-71
     */

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http
        .authorizeExchange { exchanges ->
            exchanges.pathMatchers(HttpMethod.GET).permitAll()
                .pathMatchers("/micro/accounts/**").hasRole("ACCOUNTS")
                .pathMatchers("/micro/cards/**").hasRole("CARDS")
                .pathMatchers("/micro/loans/**").hasRole("LOANS")
        }
        .oauth2ResourceServer { oAuth2ResourceServerSpec ->
            oAuth2ResourceServerSpec.jwt { jwtSpec ->
                jwtSpec.jwtAuthenticationConverter(converter())
            }
        }
        .csrf { csrfSpec ->
            csrfSpec.disable()
        }
        .build()

    private fun converter(): Converter<Jwt, Mono<AbstractAuthenticationToken>> {

        return ReactiveJwtAuthenticationConverterAdapter(
            JwtAuthenticationConverter().apply {
                setJwtGrantedAuthoritiesConverter(KeycloakRoleConverter())
            })
    }

    private class KeycloakRoleConverter : Converter<Jwt, Collection<GrantedAuthority>> {

        override fun convert(source: Jwt): Collection<GrantedAuthority> {

            val realmAccess = source.claims["realm_access"] as? Map<*, *>

            if (realmAccess.isNullOrEmpty()) return emptyList()

            val roles = realmAccess["roles"] as? List<*>

            if (roles.isNullOrEmpty()) return emptyList()

            return roles.map { role -> GrantedAuthority { "ROLE_$role" } }
        }

    }
}

