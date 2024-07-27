package example.com.services.firebase.update

import com.google.firebase.database.*
import example.com.services.firebase.ConfigLoader
import kotlinx.coroutines.suspendCancellableCoroutine
import org.slf4j.LoggerFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object FirebaseUpdate {
    private val logger = LoggerFactory.getLogger(FirebaseUpdate::class.java)

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance(ConfigLoader.config.databaseUrl)
    }

    private val updateStringRef: DatabaseReference by lazy {
        database.getReference("ktor/updateString")
    }

    /**
     * Updates the data at the /ktor/updateString path with the provided new value.
     *
     * @param newValue The new value to set at the specified path.
     * @return A success or failure message.
     */
    suspend fun updateData(newValue: String): String {
        logger.info("updateData called ...")

        return try {
            logger.info("Updating data in Firebase...")
            updateStringRef.setValueOnce(newValue)
            "Data updated successfully"
        } catch (e: Exception) {
            logger.error("Failed to update data", e)
            "Failed to update data"
        }
    }

    private suspend fun DatabaseReference.setValueOnce(value: Any) =
        suspendCancellableCoroutine<Unit> { continuation ->
            setValue(value) { error, _ ->
                if (error == null) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(error.toException())
                }
            }
        }
}
