package example.com.services.firebase.post

import com.google.firebase.database.*
import kotlinx.coroutines.suspendCancellableCoroutine
import org.slf4j.LoggerFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import example.com.services.firebase.ConfigLoader

object FirebasePost {
    private val logger = LoggerFactory.getLogger(FirebasePost::class.java)

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance(ConfigLoader.config.databaseUrl)
    }

    private val postStringRef: DatabaseReference by lazy {
        database.getReference("ktor/postString")
    }

    suspend fun postData(): String {
        logger.info("postData called ...")

        return try {
            logger.info("Posting data to Firebase...")
            postStringRef.setValueOnce("rabbit")
            "Data posted successfully"
        } catch (e: Exception) {
            logger.error("Failed to post data", e)
            "Failed to post data"
        }
    }

    private suspend fun DatabaseReference.setValueOnce(value: Any) =
        suspendCancellableCoroutine<Unit> { continuation ->
            setValue(value, null
            ) { error, ref ->
                if (error == null) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(error.toException())
                }
            }
        }

}
