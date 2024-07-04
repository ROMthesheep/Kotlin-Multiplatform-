package Presentacion

import Domain.ExpenseRepository
import Model.Expense
import Model.ExpenseCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class ExpensesUIState(
    val expenses: List<Expense> = emptyList(),
    val total: Double = 0.0
)

class ExpensesViewModel(private val repo: ExpenseRepository): ViewModel() {
    private val _uiState = MutableStateFlow(ExpensesUIState())
    val uiState = _uiState.asStateFlow()
    private var allExpense = repo.getAllExpenses()

    init {
        getAllExpenses()
    }

    private fun updateExpenseList() {
        viewModelScope.launch {
            allExpense = repo.getAllExpenses().toMutableList()
            updateState()
        }
    }

    private fun getAllExpenses() {
        repo.getAllExpenses()
        updateExpenseList()
    }

    fun addExpense(expense: Expense) {
        repo.addExpense(expense)
        updateExpenseList()
    }

    fun editExpense(expense: Expense) {
        repo.editExpense(expense)
        updateExpenseList()
    }

    fun deleteExpense(expense: Expense) {
        repo.delete(expense)
        updateExpenseList()
    }

    private fun updateState() {
        _uiState.update { state ->
            state.copy(expenses = allExpense, total = allExpense.sumOf { it.amount })
        }
    }

    fun getExpenseWithID(id: Long): Expense {
        return allExpense.first { it.id == id}
    }

    fun getCategories(): List<ExpenseCategory> {
        return repo.getCategories()
    }
}