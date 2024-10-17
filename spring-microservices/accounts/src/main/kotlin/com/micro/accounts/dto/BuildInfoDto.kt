package com.micro.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "BuildInfo",
    description = "Build information"
)
data class BuildInfoDto(
    @Schema(description = "Application version")
    val appVersion: String,
    @Schema(description = "Java version")
    val javaVersion: String,
)