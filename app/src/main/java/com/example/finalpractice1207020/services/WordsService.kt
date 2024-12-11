package com.example.finalpractice1207020.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/** Response class from the Lipsum API. */
class LipsumResponse: ArrayList<String>()

/** Interface for the words API, will be instantiated by Retrofit. */
interface WordsService {
    /**
     * Get some random lorem ipsum text from the API.
     * @return a list of random lipsum sentences
     */
    @GET("?type=meat-and-filler")
    suspend fun getLipsum(): LipsumResponse
}

/** Singleton object to use the words API. */
object RetrofitProvider {
    /** Retrofit object */
    private val retrofit: Retrofit by lazy { makeRetrofit() }
    /** words API service instance, created by Retrofit */
    val wordsService: WordsService by lazy { retrofit.create(WordsService::class.java) }

    /**
     * Create the Retrofit object.
     * @return created Retrofit
     */
    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            // set the base URL for the API
            .baseUrl("https://baconipsum.com/api/")
            // add a GSON converter factory for the response data
            .addConverterFactory(GsonConverterFactory.create())
            // and finally build Retrofit
            .build()
    }
}