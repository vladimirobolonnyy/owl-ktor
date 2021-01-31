package com.obolonnyy.owl_ktor.repository

import com.obolonnyy.owl_ktor.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class EnglishRepositoryImpl : EnglishRepository {

    override suspend fun addEnglish(eng: String, rus: String): EnglishDao? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = EnglishObjects.insert { dao ->
                dao[english] = eng
                dao[russian] = rus
            }
        }
        return rowToEnglishDao(statement?.resultedValues?.get(0))
    }

    override suspend fun getAllEnglishWords(): List<EnglishDao> {
        return transaction {
            EnglishObjects.selectAll()
                .mapNotNull { rowToEnglishDao(it) }
                .sortedBy { it.id }
        }
    }

    override suspend fun getEnglishWords(id: Int): EnglishDao? = transaction {
        EnglishObjects.select { EnglishObjects.id.eq(id) }
            .firstOrNull()
            .let { rowToEnglishDao(it) }
    }

    override suspend fun updateEnglish(id: Int, newEng: String, newRus: String): EnglishDao? = transaction {
        EnglishObjects.update({ EnglishObjects.id.eq(id) }) {
            it[russian] = newRus
            it[english] = newEng
        }
        EnglishObjects
            .select { EnglishObjects.id.eq(id) }
            .map { rowToEnglishDao(it) }
            .singleOrNull()
    }

    private fun rowToEnglishDao(row: ResultRow?): EnglishDao? {
        if (row == null) {
            return null
        }
        return EnglishDao(
            id = row[EnglishObjects.id],
            english = row[EnglishObjects.english],
            russian = row[EnglishObjects.russian],
        )
    }
}