package example.com.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {
    authentication {
        // @@ Basic Authentication
        // The client sends an Authorization header with the request.
        // This header contains the credentials encoded in Base64.
        // The server decodes these credentials and validates them.
        basic(name = "myauth1") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == "wesley.2510" && credentials.password == "test123") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }

        // @@ Form Authentication
        // The client sends a POST request with form data containing the username and password.
        // The server validates these credentials based on the form parameters specified.
        form(name = "myauth2") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                if (credentials.name == "wesley.2510" && credentials.password == "test321") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
            challenge {
                /**/
            }
        }
    }
    routing {
        authenticate("myauth1") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
        authenticate("myauth2") {
            get("/protected/route/form") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
    }
}