package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class  ArticlesService(private val httpClient: HttpClient) {
    private val country = "us"
    private val category = "business"
    private val apiKey = Secrets.apiKey

    suspend fun fecthArticles(): List<ArticleRaw>  {
        val response: ArticlesResponse = httpClient.get("https://newsapi.org/v2/top-headlines?country=$country&category=$category&apiKey=$apiKey").body()
        return response.articles
    }
}