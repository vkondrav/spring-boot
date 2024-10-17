package com.micro.cards.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [MobileNumberValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidMobileNumber(
    val message: String = "Mobile number must be a valid phone number",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

private class MobileNumberValidator : ConstraintValidator<ValidMobileNumber, String> {
    private val mobileNumberPattern = "^(\\+\\d{1,3}[- ]?)?\\d{10}\$".toRegex()

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        value ?: return false
        return mobileNumberPattern.matches(value)
    }
}