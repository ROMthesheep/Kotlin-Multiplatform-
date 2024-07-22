package com.petros.efthymiou.dailypulse.articles

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArticleRaw(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val desc: String?,
    @SerialName("publishedAt")
    val date: String,
    @SerialName("urlToImage")
    val imageUrl: String?
)