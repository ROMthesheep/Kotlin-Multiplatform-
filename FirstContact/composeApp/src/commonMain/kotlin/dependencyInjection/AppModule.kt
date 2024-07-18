package dependencyInjection

import data.ExpenseRepositoryImpl
import Domain.ExpenseRepository
import Presentacion.ExpensesViewModel
import com.expenseApp.db.Database
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun appModule(appDatabase: Database) = module {
    single<HttpClient> { HttpClient { install(ContentNegotiation) { json() } } }
    single<ExpenseRepository> { ExpenseRepositoryImpl(appDatabase, get()) }
    factory { ExpensesViewModel(get()) }
}