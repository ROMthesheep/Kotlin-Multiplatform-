package data

import Domain.ExpenseRepository
import Model.Expense
import Model.ExpenseCategory
import com.expenseApp.db.Database

class ExpenseRepositoryImpl(private val appDatabase: Database): ExpenseRepository {

    private val queries = appDatabase.expensesDBQueries
    override fun getAllExpenses(): List<Expense> {
        return queries.selectAll().executeAsList().map {
            Expense(
                id = it.id,
                amount = it.amount,
                category = ExpenseCategory.valueOf(it.categoryName),
                description = it.description
            )
        }
    }

    override fun addExpense(expense: Expense) {
        queries.transaction {
            queries.insert(
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
    }

    override fun editExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
    }

    override fun getCategories(): List<ExpenseCategory> {
        return queries.selectAllCategories().executeAsList().map { ExpenseCategory.valueOf(it) }
    }

    override fun delete(expense: Expense) {
        queries.transaction {
            queries.deleteExpense(expense.id)
        }
    }
}