package com.micro.loans.audit

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

@Component("auditorProvider")
class AuditAware : AuditorAware<String> {
    override fun getCurrentAuditor() = Optional.of("LOANS_SYSTEM")
}