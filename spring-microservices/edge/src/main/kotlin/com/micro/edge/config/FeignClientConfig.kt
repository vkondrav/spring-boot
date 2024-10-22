package com.micro.edge.config

import feign.codec.Decoder
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class FeignClientConfig {
    @Bean
    fun feignDecoder(): Decoder = ResponseEntityDecoder(
        SpringDecoder {
            HttpMessageConverters(MappingJackson2HttpMessageConverter())
        }
    )
}