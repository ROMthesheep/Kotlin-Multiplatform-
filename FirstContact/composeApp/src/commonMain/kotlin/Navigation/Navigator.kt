package Navigation

import Data.ExpenseManager
import Data.ExpenseRepositoryImpl
import Presentacion.ExpensesViewModel
import UI.ExpensesDetailScreen
import UI.ExpensesScreen
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import getColorsTheme
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.viewmodel.viewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Navigation(modifier: Modifier = Modifier, navigator: Navigator) {
    val colors = getColorsTheme()
    val viewModel = koinViewModel(ExpensesViewModel::class) { parametersOf() }

    NavHost(
        modifier = modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(modifier = modifier, uiState = uiState) { expense ->
                println("Expense clickado ${expense.id}")
                navigator.navigate("/addExpenses/${expense.id}")
            }
        }
        scene(route = "/addExpenses/{id}?") { backStack ->
            val _idFromPath = backStack.path<Long>("id")
            val isAddExpense = _idFromPath?.let { id -> viewModel.getExpenseWithID(id) }

            ExpensesDetailScreen(expenseToEdit = isAddExpense, categoriesList = viewModel.getCategories()) { expense ->
                if (isAddExpense == null) {
                    viewModel.addExpense(expense)
                } else {
                    viewModel.editExpense(expense)
                }
                navigator.popBackStack()
            }
        }
    }
}