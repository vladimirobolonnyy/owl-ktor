package com.obolonnyy.owl_ktor

import com.obolonnyy.owl_ktor.data.EnglishDto
import com.obolonnyy.owl_ktor.repository.EnglishDao
import com.obolonnyy.owl_ktor.repository.EnglishRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val WORDS = "$API_VERSION/words"

fun Routing.words(
    db: EnglishRepository
) {
    get("$WORDS/all") {
        val result = db.getAllEnglishWords()
        call.respond(HttpStatusCode.OK, result)
    }

    post("$WORDS/add") {
        val data = call.receiveOrNull<EnglishDto>()
        when {
            data == null -> call.respond(HttpStatusCode.BadRequest, "need data")
            data.english == null -> call.respond(HttpStatusCode.BadRequest, "english shouldn't be null")
            data.russian == null -> call.respond(HttpStatusCode.BadRequest, "russian shouldn't be null")
            else -> {
                val result = db.addEnglish(data.english, data.russian)
                if (result == null) {
                    call.respond(HttpStatusCode.InternalServerError, "added data, but returned null")
                } else {
                    call.respond(HttpStatusCode.OK, result)
                }
            }
        }
    }

    post("$WORDS/addall") {
        val list = call.receiveOrNull<List<EnglishDto>>()
        if (list.isNullOrEmpty()) {
            return@post call.respond(HttpStatusCode.BadRequest, "need data")
        }
        list.forEach { data ->
            when {
                data.english == null -> return@post call.respond(
                    HttpStatusCode.BadRequest,
                    "english shouldn't be null. Object $data"
                )
                data.russian == null -> return@post call.respond(
                    HttpStatusCode.BadRequest,
                    "russian shouldn't be null. Object $data"
                )
                else -> {
                    db.addEnglish(data.english, data.russian)
                        ?: return@post call.respond(
                            HttpStatusCode.InternalServerError,
                            "added data $data, but returned null"
                        )
                }
            }
        }
        call.respond(HttpStatusCode.OK)
    }

    post("$WORDS/update") {
        val data = call.receiveOrNull<EnglishDto>()
        when {
            data == null -> call.respond(HttpStatusCode.BadRequest, "need data")
            data.id == null -> call.respond(HttpStatusCode.BadRequest, "id shouldn't be null")
            data.english == null -> call.respond(HttpStatusCode.BadRequest, "english shouldn't be null")
            data.russian == null -> call.respond(HttpStatusCode.BadRequest, "russian shouldn't be null")
            else -> {
                val has = db.getEnglishWords(data.id)
                if (has != null) {
                    val result = db.updateEnglish(data.id, data.english, data.russian)
                    if (result == null) {
                        call.respond(HttpStatusCode.InternalServerError, "added data, but returned null")
                    } else {
                        call.respond(HttpStatusCode.OK, result)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "hasn't value with id ${data.id}")
                }
            }
        }
    }
}