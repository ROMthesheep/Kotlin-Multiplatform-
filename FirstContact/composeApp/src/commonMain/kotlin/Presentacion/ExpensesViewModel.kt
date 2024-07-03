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
    private val allExpense = repo.getAllExpenses()

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            updateState()
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repo.addExpense(expense)
            updateState()
        }
    }

    fun editExpense(expense: Expense) {
        viewModelScope.launch {
            repo.editExpense(expense)
            updateState()
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repo.delete(expense)
            updateState()
        }
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