package com.obolonnyy.owl_ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.helloWorld() {
    get("/") {
        call.respondText("Hello, ktor!", contentType = ContentType.Text.Plain)
    }

    get("/android") {
        call.respondText("Привет, Орлы!", contentType = ContentType.Text.Plain)
    }

    get("/ios") {
        call.respondText("Привет, петушары!", contentType = ContentType.Text.Plain)
    }
}