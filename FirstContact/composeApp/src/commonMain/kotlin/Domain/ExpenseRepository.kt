package Domain

import Model.Expense
import Model.ExpenseCategory

interface ExpenseRepository {
    suspend fun getAllExpenses(): List<Expense>
    suspend fun addExpense(expense: Expense)
    suspend fun editExpense(expense: Expense)
    fun getCategories(): List<ExpenseCategory>

    suspend fun delete(expense: Expense)
}