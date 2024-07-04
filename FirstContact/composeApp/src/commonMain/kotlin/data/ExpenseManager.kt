package data

import Model.Expense
import Model.ExpenseCategory

object ExpenseManager {
    private  var currentId = 1L
    val fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 70.0,
            category = ExpenseCategory.GROCERIES,
            description = "Weekly buy"
        ),
        Expense(
            id = currentId++,
            amount = 1234.0,
            category = ExpenseCategory.HOUSE,
            description = "New sofa"
        ),
        Expense(
            id = currentId++,
            amount = 18.0,
            category = ExpenseCategory.SNACKS,
            description = "Palomitas"
        ),
        Expense(
            id = currentId++,
            amount = 3.0,
            category = ExpenseCategory.COFFEE,
            description = "Coffee with croissant"
        ),
        Expense(
            id = currentId++,
            amount = 30870.0,
            category = ExpenseCategory.CAR,
            description = "New Toyota"
        ),
        Expense(
            id = currentId++,
            amount = 30.0,
            category = ExpenseCategory.OTHER,
            description = "Lent money to Juan"
        ),

    )

    fun addNewExpense(expense: Expense) {
        fakeExpenseList.add(expense.copy(id = currentId++))
    }

    fun editExpense(expense: Expense) {
        val index = fakeExpenseList.indexOfFirst { it.id == expense.id }
        if (index != -1) {
            fakeExpenseList[index] = fakeExpenseList[index].copy(
                amount = expense.amount,
                category = expense.category,
                description = expense.description
            )
        }
    }

    fun deleteExpense(expense: Expense) {
        fakeExpenseList.removeAll { it.id == expense.id }
    }

    fun getCategories(): List<ExpenseCategory> {
        return listOf(
            ExpenseCategory.GROCERIES,
            ExpenseCategory.PARTY,
            ExpenseCategory.COFFEE,
            ExpenseCategory.CAR,
            ExpenseCategory.HOUSE,
            ExpenseCategory.OTHER,
        )
    }
}