package example.com.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val messge: String
)
