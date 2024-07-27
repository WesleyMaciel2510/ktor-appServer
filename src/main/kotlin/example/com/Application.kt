package example.com

import example.com.plugins.*
import example.com.services.firebase.FirebaseConfig
import example.com.services.firebase.post.FirebasePost
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.server.plugins.statuspages.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
    //embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureRouting()
}
