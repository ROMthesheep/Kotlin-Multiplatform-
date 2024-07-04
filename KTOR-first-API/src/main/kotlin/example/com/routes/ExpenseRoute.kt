package example.com.routes

import example.com.data.model.ErrorResponse
import example.com.data.model.Expense
import example.com.data.model.expenses
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import javax.security.auth.callback.Callback

fun Route.expensesRouting() {
    get(path = "/expenses") {
        if (expenses.isEmpty()) {
            call.respondText { "No expenses found " }
        } else {
            call.respond(status = HttpStatusCode.OK, expenses)
        }
    }

    get(path = "expenses/{id}") {
        val id = call.parameters["id"]?.toLongOrNull()
        val expense = expenses.find { it.id == id }
        if (id == null || expense == null ) {
            call.respond(HttpStatusCode.NotFound, ErrorResponse("Expense not found"))
            return@get
        }
        call.respond(HttpStatusCode.OK, expense)
    }

    post("/expenses") {
        val expense = call.receive<Expense>()
        val maxId = expenses.maxOf { it.id } + 1
        expenses.add(expense.copy(id = maxId))
        call.respond(HttpStatusCode.OK, "De super puta madre socio")
    }

    put("expenses/{id}") {
        val id = call.parameters["id"]?.toLongOrNull()
        val expense = call.receive<Expense>()
        if (id == null || id !in 0 until expenses.size) {
            call.respond(HttpStatusCode.NotFound, ErrorResponse("Expense not found"))
            return@put
        }
        val index = expenses.indexOfFirst { it.id == id }
        expenses[index] = expense.copy(id = id)
        call.respond(HttpStatusCode.OK, "De super puta madre socio")
    }

    delete("expenses/{id}") {
        val id = call.parameters["id"]?.toLongOrNull()
        val expense = expenses.find { it.id == id }
        if (id == null || expense == null) {
            call.respond(HttpStatusCode.NotFound, ErrorResponse("Expense not found"))
            return@delete
        }
        expenses.removeIf { it.id == id }
        call.respond(HttpStatusCode.OK, "De super puta madre socio")
    }
}