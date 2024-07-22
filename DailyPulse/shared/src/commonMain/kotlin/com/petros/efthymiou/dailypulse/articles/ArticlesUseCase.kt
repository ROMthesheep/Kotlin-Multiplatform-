package com.petros.efthymiou.dailypulse.articles

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.math.abs

class ArticlesUseCase(private val articleService: ArticlesService) {

    suspend fun getArticles(): List<Article> {
        val raws = articleService.fecthArticles()
        return mapArticles(raws)
    }

    private fun mapArticles(raws: List<ArticleRaw>): List<Article> = raws.map { raw ->
        Article(
            raw.title,
            raw.desc ?: "Click to find out more",
            getDaysAgo(raw.date),
            raw.imageUrl ?: "https://image.cnbcfm.com/api/v1/image/107326078-1698758530118-gettyimages-1765623456-wall26362_igj6ehhp.jpeg?v=1698758587&w=1920&h=1080"
        )
    }

    private fun getDaysAgo(date: String): String {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val days = today.daysUntil(
            Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date
        )
        return when {
            abs(days) > 1 ->"${abs(days)} days ago"
            abs(days) == 1 -> "Yesterday"
            else -> "Today"
        }
    }
}