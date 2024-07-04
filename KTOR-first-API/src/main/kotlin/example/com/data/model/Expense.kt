package example.com.data.model

import kotlinx.serialization.Serializable

val expenses = mutableListOf(
    Expense(id = 1, amount = 6.7, category = "GROCERIES", description = "singulis"),
    Expense(id = 2, amount = 10.11, category = "PARTY", description = "urbanitas"),
    Expense(id = 3, amount = 14.15, category = "SNACKS", description = "magna"),
    Expense(id = 4, amount = 18.19, category = "COFFEE", description = "disputationi"),
    Expense(id = 5, amount = 22.23, category = "CAR", description = "ante"),
    Expense(id = 6, amount = 2.7, category = "SNACKS", description = "webo"),
    Expense(id = 7, amount = 26.27, category = "OTHER", description = "eget"),
)

@Serializable
data class Expense(
    val id: Long = -1,
    val amount: Double,
    val category: String,
    val description: String
)