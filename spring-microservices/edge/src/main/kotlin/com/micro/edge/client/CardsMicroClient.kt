package com.micro.edge.client

import com.micro.cards.api.CardsApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "cards", fallback = CardsMicroClientFallback::class)
interface CardsMicroClient : CardsApi

