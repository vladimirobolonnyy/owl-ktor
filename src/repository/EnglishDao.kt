package com.obolonnyy.owl_ktor.repository

import java.security.Principal
import java.io.Serializable

data class EnglishDao(
    val id: Int,
    val english: String,
    val russian: String,
) : Serializable, Principal {

    override fun getName(): String {
        return english
    }
}