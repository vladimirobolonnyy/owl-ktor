package com.obolonnyy.owl_ktor

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        gson {
        }
    }

//    val client = HttpClient {
//        install(JsonFeature) { serializer = GsonSerializer() }
//    }
//    runBlocking {
//        // Sample for making a HTTP Client request
//        val message = client.post<JsonSampleClass> {
//            url("http://127.0.0.1:8080/path/to/endpoint")
//            contentType(ContentType.Application.Json)
//            body = JsonSampleClass(hello = "world")
//        }
//    }

    routing {
        this.helloWorld()

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }

        post("/data") {
            val post = call.receiveOrNull<Data>()
            call.respond(mapOf("hello" to "world", post to "got"))
        }
    }
}

data class Data(val r1: String, val r2: String)