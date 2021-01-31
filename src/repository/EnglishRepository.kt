package com.obolonnyy.owl_ktor.repository

interface EnglishRepository {
    suspend fun addEnglish(eng: String, rus: String): EnglishDao?
    suspend fun getAllEnglishWords(): List<EnglishDao?>
    suspend fun getEnglishWords(id: Int): EnglishDao?
    suspend fun updateEnglish(id: Int, newEng: String, newRus: String): EnglishDao?
}