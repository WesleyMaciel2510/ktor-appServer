package example.com.plugins

import example.com.services.firebase.delete.FirebaseDelete
import example.com.services.firebase.get.FirebaseGet
import example.com.services.firebase.post.FirebasePost
import example.com.services.firebase.update.FirebaseUpdate
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/firebase") {
            val data = withContext(Dispatchers.IO) {
                FirebaseGet.getData()
            }
            call.respondText("Firebase data value: $data")
        }
        post("/firebasePost") {
            try {
                val result = FirebasePost.postData()
                call.respondText(result)
            } catch (e: Exception) {
                call.respondText("Error: ${e.localizedMessage}", status = HttpStatusCode.InternalServerError)
            }
        }
        put("/updateString") {
            val newValue = call.receive<String>()
            val result = FirebaseUpdate.updateData(newValue)
            call.respondText(result)
        }
        delete("/deleteString") {
            val result = FirebaseDelete.deleteData()
            call.respondText(result)
        }
    }
}
