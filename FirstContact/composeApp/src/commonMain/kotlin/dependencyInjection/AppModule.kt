package dependencyInjection

import data.ExpenseRepositoryImpl
import Domain.ExpenseRepository
import Presentacion.ExpensesViewModel
import com.expenseApp.db.Database
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun appModule(appDatabase: Database) = module {
    single<ExpenseRepository> { ExpenseRepositoryImpl(appDatabase) }
    factory { ExpensesViewModel(get()) }
}