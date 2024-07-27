package example.com.services.firebase.delete

import com.google.firebase.database.*
import example.com.services.firebase.ConfigLoader
import kotlinx.coroutines.suspendCancellableCoroutine
import org.slf4j.LoggerFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object FirebaseDelete {
    private val logger = LoggerFactory.getLogger(FirebaseDelete::class.java)

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance(ConfigLoader.config.databaseUrl)
    }

    private val deleteStringRef: DatabaseReference by lazy {
        database.getReference("ktor/deleteString")
    }

    /**
     * Deletes the data at the /ktor/deleteString path.
     *
     * @return A success or failure message.
     */
    suspend fun deleteData(): String {
        logger.info("deleteData called ...")

        return try {
            logger.info("Deleting data from Firebase...")
            deleteStringRef.removeValueOnce()
            "Data deleted successfully"
        } catch (e: Exception) {
            logger.error("Failed to delete data", e)
            "Failed to delete data"
        }
    }

    private suspend fun DatabaseReference.removeValueOnce() =
        suspendCancellableCoroutine<Unit> { continuation ->
            removeValue { error, _ ->
                if (error == null) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(error.toException())
                }
            }
        }
}
