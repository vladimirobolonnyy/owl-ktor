package com.obolonnyy.owl_ktor.repository

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object EnglishObjects : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val english = varchar("english", 64)
    val russian = varchar("russian", 256)
}