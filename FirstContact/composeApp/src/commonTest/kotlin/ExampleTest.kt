import Model.Expense
import Model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains

class ExampleTest {
    @Test
    fun expense_model_list_test() {
        // Given
        val expenseList = mutableListOf<Expense>()
        val expense = Expense(id = 1, amount = 4.5,  category = ExpenseCategory.CAR, description = "cosa")
        // When
        expenseList.add(expense)
        // Then
        assertContains(expenseList, expense)
    }
}