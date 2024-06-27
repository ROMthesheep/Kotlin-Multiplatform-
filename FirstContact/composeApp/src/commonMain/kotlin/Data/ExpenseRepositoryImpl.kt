package Data

import Domain.ExpenseRepository
import Model.Expense
import Model.ExpenseCategory

class ExpenseRepositoryImpl(private val expenseManager: ExpenseManager): ExpenseRepository {
    override fun getAllExpenses(): List<Expense> {
        return expenseManager.fakeExpenseList
    }

    override fun addExpense(expense: Expense) {
        expenseManager.addNewExpense(expense)
    }

    override fun editExpense(expense: Expense) {
        expenseManager.editExpense(expense)
    }

    override fun getCategories(): List<ExpenseCategory> {
        return expenseManager.getCategories()
    }

    override fun delete(expense: Expense) {
        expenseManager.deleteExpense(expense)
    }
}