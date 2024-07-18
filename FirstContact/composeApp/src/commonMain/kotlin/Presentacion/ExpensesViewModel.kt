package Presentacion

import  Domain.ExpenseRepository
import Model.Expense
import Model.ExpenseCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class ExpensesUIState {
    object  Loading: ExpensesUIState()
    data class Success(val expenses: List<Expense>, val total: Double): ExpensesUIState()
    data class Error(val msg: String): ExpensesUIState()
}

class ExpensesViewModel(private val repo: ExpenseRepository): ViewModel() {
    private val _uiState = MutableStateFlow<ExpensesUIState>(ExpensesUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getExpenseList()
    }

    private fun getExpenseList() {
        viewModelScope.launch {
            try {
                val expenses = repo.getAllExpenses()
                _uiState.value = ExpensesUIState.Success(expenses, expenses.sumOf { it.amount })
            } catch (e: Exception) {
                _uiState.value = ExpensesUIState.Error(e.message ?: "Cagaste")
            }
        }
    }

    private suspend fun updateExpenseList() {
        try {
            val expenses = repo.getAllExpenses()
            _uiState.value = ExpensesUIState.Success(expenses, expenses.sumOf { it.amount })
        } catch (e: Exception) {
            _uiState.value = ExpensesUIState.Error(e.message ?: "Cagaste")
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.addExpense(expense)
                updateExpenseList()
            } catch (e: Exception) {
                _uiState.value = ExpensesUIState.Error(e.message ?: "Cagaste")
            }
        }
    }

    fun editExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.editExpense(expense)
                updateExpenseList()
            } catch (e: Exception) {
                _uiState.value = ExpensesUIState.Error(e.message ?: "Cagaste")
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.delete(expense)
                updateExpenseList()
            } catch (e: Exception) {
                _uiState.value = ExpensesUIState.Error(e.message ?: "Cagaste")
            }
        }
    }

    fun getExpenseWithID(id: Long): Expense? {
        return (_uiState.value as? ExpensesUIState.Success)?.expenses?.firstOrNull { it.id == id}
    }

    fun getCategories(): List<ExpenseCategory> {
        return repo.getCategories()
    }
}