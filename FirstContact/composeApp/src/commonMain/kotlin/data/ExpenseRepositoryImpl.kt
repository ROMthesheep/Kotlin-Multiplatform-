package data

import Domain.ExpenseRepository
import Model.Expense
import Model.ExpenseCategory
import Model.NetworkExpense
import com.expenseApp.db.Database
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val BASE_URL = "http://192.168.1.101:8080"

class ExpenseRepositoryImpl(private val appDatabase: Database, private val httpClient: HttpClient): ExpenseRepository {

    private val queries = appDatabase.expensesDBQueries

    override suspend fun getAllExpenses(): List<Expense> {
        return if (queries.selectAll().executeAsList().isEmpty()) {
            val networkResponse = httpClient.get("$BASE_URL/expenses").body<List<NetworkExpense>>()
            val expenses = networkResponse.map { Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.category),
                    description = it.description
                )
            }
            expenses.forEach {
                queries.insert(it.amount, it.category.name, it.description)
            }

            expenses
        } else {
            queries.selectAll().executeAsList().map {
                Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.categoryName),
                    description = it.description
                )
            }
        }
    }

    override suspend fun addExpense(expense: Expense) {
        queries.transaction {
            queries.insert(
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
        httpClient.post("$BASE_URL/expenses") {
            contentType(ContentType.Application.Json)
            setBody(NetworkExpense(amount = expense.amount, category = expense.category.name, description = expense.description))
        }
    }

    override suspend fun editExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
        httpClient.put("$BASE_URL/expenses/${expense.id}") {
            contentType(ContentType.Application.Json)
            setBody(NetworkExpense(id = expense.id, amount = expense.amount, category = expense.category.name, description = expense.description))
        }
    }

    override fun getCategories(): List<ExpenseCategory> {
        return queries.selectAllCategories().executeAsList().map { ExpenseCategory.valueOf(it) }
    }

    override suspend fun delete(expense: Expense) {
        queries.transaction {
            queries.deleteExpense(expense.id)
        }
    }
}