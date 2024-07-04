package example.com.plugins

import example.com.routes.expensesRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        expensesRouting()
    }
}
