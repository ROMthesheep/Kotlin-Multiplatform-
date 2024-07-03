package dependencyInjection

import Data.ExpenseManager
import Data.ExpenseRepositoryImpl
import Domain.ExpenseRepository
import Presentacion.ExpensesViewModel
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun appModule() = module {
    single { ExpenseManager }.withOptions { createdAtStart() }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    factory { ExpensesViewModel(get()) }
}