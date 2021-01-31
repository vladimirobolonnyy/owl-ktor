package com.obolonnyy.owl_ktor.data

import java.security.Principal
import java.io.Serializable

data class EnglishDto(
    val id: Int?,
    val english: String?,
    val russian: String?,
) : Serializable