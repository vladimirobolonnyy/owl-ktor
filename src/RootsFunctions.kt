package com.obolonnyy.owl_ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.helloWorld() {
    get("/") {
        call.respondText("Hello, ktor1!", contentType = ContentType.Text.Plain)
    }

    // Static feature. Try to access `/static/ktor_logo.svg`
    static("/static") {
        resources("static")
    }
}