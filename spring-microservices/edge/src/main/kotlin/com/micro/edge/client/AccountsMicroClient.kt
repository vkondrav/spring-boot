package com.micro.edge.client

import com.micro.accounts.api.AccountsApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "accounts", fallback = AccountsMicroClientFallback::class)
interface AccountsMicroClient : AccountsApi

