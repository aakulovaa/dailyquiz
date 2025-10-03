package com.example.dailyquiz.data.remote.api

import com.example.dailyquiz.data.remote.models.QuizApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 5,
        @Query("category") category: Int = 9,
        @Query("difficulty") difficulty: String = "easy",
        @Query("type") type: String = "multiple"
    ): QuizApiResponse
}