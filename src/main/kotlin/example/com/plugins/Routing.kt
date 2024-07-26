package example.com.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.netty.*
import io.ktor.server.engine.*
import io.ktor.server.auth.*

fun Application.configureRouting() {
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("Hello World!")
            }
            get("/newRoute") {
                call.respondText("New Route!")
            }
        }
    }.start(wait = true)
}
