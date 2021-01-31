package com.obolonnyy.owl_ktor

import com.obolonnyy.owl_ktor.repository.DatabaseFactory
import com.obolonnyy.owl_ktor.repository.EnglishRepositoryImpl
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*

const val API_VERSION = "/v1"

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

    DatabaseFactory.init()
    val db = EnglishRepositoryImpl()

    routing {
        this.helloWorld()
        this.words(db)
    }

}